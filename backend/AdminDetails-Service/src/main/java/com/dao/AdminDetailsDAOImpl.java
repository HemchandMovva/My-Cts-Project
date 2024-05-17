package com.dao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.entities.AdminDetails;
import com.exceptions.ExceptionMessages;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidEmailIdException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.exceptions.InvalidPasswordException;
import com.exceptions.InvalidPhoneNumberException;
import com.feign.AdminDetailsFeign;
import com.repository.AdminDetailsRepository;
import com.response.AdminDetailsResponse;
import com.response.StatesResponse;

@Service
@ComponentScan("com.configuration com.feign")
public class AdminDetailsDAOImpl {

	@Autowired
	private AdminDetailsRepository adminDetailsRepository;
	
	@Autowired
	private ExceptionMessages exceptionMessages;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AdminDetailsFeign adminDetailsFeign;
	
	@Autowired
	private Validation validation;
	
	
	
//	--------------------MAPPING TO THE ADMINDETAILS RESPONSE CLASS--------------------------------------------------------------------
	
	public AdminDetailsResponse mapToAdminDetailsResponse(AdminDetails adminDetails) throws InvalidIdException {
		AdminDetailsResponse adminDetailsResponse = modelMapper.map(adminDetails, AdminDetailsResponse.class);
		StatesResponse statesResponse = adminDetailsFeign.selectById(adminDetails.getStateId());
		adminDetailsResponse.setStatesResponse(statesResponse);
		return adminDetailsResponse;
	}
	
//	--------------------1. SELECT QUERY METHODS----------------------------------------------------------------------------------------
	public AdminDetailsResponse selectById(String id) throws InvalidIdException {
		AdminDetails adminDetails = adminDetailsRepository.findByAdminId(id);
		if(adminDetails != null) {
			AdminDetailsResponse adminDetailsResponse = mapToAdminDetailsResponse(adminDetails);
			return adminDetailsResponse;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(id));
		}
	}
	
//	--------------------2. INSERT QUERY METHODS-----------------------------------------------------------------------------------------
	public String insertDetails(AdminDetails obj, String id) throws InvalidDetailsException{
		AdminDetails existedDetails = adminDetailsRepository.findByAdminId(obj.getAdminId());
		if(existedDetails == null) {
			obj.setStateId(id);
			adminDetailsRepository.save(obj);
			return "Succesfully added admin details with admin id: "+obj.getAdminId();
		}else {
			throw new InvalidDetailsException(exceptionMessages.getInvalidDetailsExceptionMessage(obj));
		}	
	}
	
//	---------------------3. UPDATE QUERY METHODS-----------------------------------------------------------------------------------------
	public String updateName(String adminId, String name) throws InvalidIdException, InvalidNameException {
		AdminDetails adminDetails = adminDetailsRepository.findByAdminId(adminId);
		if(adminDetails != null) {
			if(validation.isValidName(name)) {
				adminDetails.setAdminName(name);
				//mapping to response
				AdminDetailsResponse adminDetailsResponse = mapToAdminDetailsResponse(adminDetails);
				adminDetailsRepository.save(adminDetails);
				return "suffessfully modified admin name belongs to "+adminDetailsResponse.getStatesResponse().getStateName();
			}else{
				throw new InvalidNameException(exceptionMessages.getInvalidNameExceptionMessage(name));
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(adminId));
		}
	}

	public String updatePhone(String adminId, String newPhone) throws InvalidIdException, InvalidPhoneNumberException{
		AdminDetails adminDetails = adminDetailsRepository.findByAdminId(adminId);
		if(adminDetails != null) {
			if(validation.isValidPhoneNumber(newPhone)) {
				adminDetails.setAdminPhone(newPhone);
				AdminDetailsResponse adminDetailsResponse = mapToAdminDetailsResponse(adminDetails);
				adminDetailsRepository.save(adminDetails);
				return "suffessfully modified phone number of admin belongs to "+adminDetailsResponse.getStatesResponse().getStateName();
			}else {
				throw new InvalidPhoneNumberException(exceptionMessages.getInvalidPhoneNumberExceptionMessage());
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(adminId));
		}
	}
	
	public String updatePassword(String adminId, String oldPassword, String newPassword) throws InvalidPasswordException, InvalidIdException{
		AdminDetails adminDetails = adminDetailsRepository.findByAdminId(adminId);
		if(adminDetails != null) {
			if(oldPassword.equals(adminDetails.getAdminPassword())) {
				if(validation.isValidPassword(newPassword)) {
					adminDetails.setAdminPassword(newPassword);
					AdminDetailsResponse adminDetailsResponse = mapToAdminDetailsResponse(adminDetails);
					adminDetailsRepository.save(adminDetails);
					return "suffessfully modified password of admin belongs to "+adminDetailsResponse.getStatesResponse().getStateName();
				}else {
					throw new InvalidPasswordException(exceptionMessages.getInvalidPasswordExceptionMessageForNewPasswordVerification());
				}
			}else {
				throw new InvalidPasswordException(exceptionMessages.getInvalidPasswordExceptionMessageForOldPasswordVerification());
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(adminId));
		}
	}
	
	public String updateEmail(String adminId, String newEmailId) throws InvalidEmailIdException, InvalidIdException{
		AdminDetails adminDetails = adminDetailsRepository.findByAdminId(adminId);
		if(adminDetails != null) {
			if(validation.isValidEmail(newEmailId)) {
				adminDetails.setAdminEmail(newEmailId);
				AdminDetailsResponse adminDetailsResponse = mapToAdminDetailsResponse(adminDetails);
				adminDetailsRepository.save(adminDetails);
				return "suffessfully modified email address of admin belongs to "+adminDetailsResponse.getStatesResponse().getStateName();
			}else {
				throw new InvalidEmailIdException(exceptionMessages.getInvalidEmailIdExceptionMessage());
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(adminId));
		}
	}
	
//	---------------------4. DELETE QUERY METHODS-----------------------------------------------------------------------------------------
	public String deleteById(String adminId) throws InvalidIdException {
		AdminDetails adminDetails = adminDetailsRepository.findByAdminId(adminId);
		if(adminDetails != null) {
			AdminDetailsResponse adminDetailsResponse = mapToAdminDetailsResponse(adminDetails);
			String message = "Successfully deleted the admin of "+adminDetailsResponse.getStatesResponse().getStateName();
			adminDetailsRepository.deleteAdminDetailsById(adminId);
			return message;
			
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(adminId));
		}
	}
}
