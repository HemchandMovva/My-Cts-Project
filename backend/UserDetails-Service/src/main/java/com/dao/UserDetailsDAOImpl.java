package com.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.entities.UserDetails;
import com.exceptions.ExceptionMessages;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidEmailIdException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.exceptions.InvalidPasswordException;
import com.exceptions.InvalidPhoneNumberException;
import com.feign.UserDetailsStatesFeign;
import com.repository.UserDetailsRepository;
import com.response.NamewiseUserDetailsResponse;
import com.response.StatesResponse;
import com.response.StatewiseUserDetailsResponse;
import com.response.UserDetailsResponse;
import com.response.UserDetailsStateResponse;

@Component
@ComponentScan("com.configuration")
public class UserDetailsDAOImpl {
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private ExceptionMessages exceptionMessages;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserDetailsStatesFeign userDetailsStatesFeign;
	
	@Autowired
	private Validation validation;
	
	
//	--------------------GENERATING THE USER ID--------------------------------------------------------------------

//	String[] arr = new String[]{"AN", "AP", "AR", "AS", "BR", "CG", "CH", "DH", "DL", "GA", "GJ", "HP", "HR", "JH", "JK", "KA", "KL", "LA", "LD", "MH", "ML", "MN", "MP", "MZ", "NL", "OD", "PB", "PY", "RJ", "SK", "TG", "TN", "TR", "UK", "UP", "WB"};

//	private Map<String, Integer> stateUserCount = new HashMap<String, Integer>(){
//		{
//			put("AN", 0);put("AP", 0);put("AR", 0);put("AS", 0);put("BR", 0);put("CG", 0);put("CH", 0);put("DH", 0);put("DL", 0);put("GA", 0);
//			
//			put("GJ", 0);put("HP", 0);put("HR", 0);put("JH", 0);put("JK", 0);put("KA", 0);put("KL", 0);put("LA", 0);put("LD", 0);put("MH", 0);
//			
//			put("ML", 0);put("MN", 0);put("MP", 0);put("MZ", 0);put("NL", 0);put("OD", 0);put("PB", 0);put("PY", 0);put("RJ", 0);put("SK", 0);
//			
//			put("TG", 0);put("TN", 0);put("TR", 0);put("UK", 0);put("UP", 0);put("WB", 0);
//
//		}
//	};
 
//	
//	
	public String generateUserId(String stateId) {
		String userId = "USER-"+stateId+"-";
		String lastUser = userDetailsRepository.selectLastUser(stateId);
		int cnt;
		if(lastUser==null) {
			cnt=0;
		}else {
			cnt = Integer.parseInt(lastUser.substring(8));
		}
		return userId+String.format("%05d", cnt+1);
		
	}
	
	
//	--------------------MAPPING TO RESPONSE CLASSES--------------------------------------------------------------------
	public UserDetailsStateResponse mapToUserDetailsStateResponse(UserDetails userDetails) throws InvalidIdException {
		UserDetailsStateResponse userDetailsResponse = modelMapper.map(userDetails, UserDetailsStateResponse.class);
		StatesResponse statesResponse = userDetailsStatesFeign.selectById(userDetails.getStateId());
		userDetailsResponse.setStatesResponse(statesResponse);
		return userDetailsResponse;
	}
	
	public StatewiseUserDetailsResponse mapToStatewiseUserDetailsResponse(List<UserDetails> userDetailsList, StatesResponse statesResponse) {
		List<UserDetailsResponse> userDetailsResponseList = userDetailsList.stream().map(x -> modelMapper.map(x, UserDetailsResponse.class)).collect(Collectors.toList());
		StatewiseUserDetailsResponse statewiseUserDetailsResponse = new StatewiseUserDetailsResponse(statesResponse, userDetailsResponseList);
		return statewiseUserDetailsResponse;
	}
	
	public NamewiseUserDetailsResponse mapToNamewiseUserDetailsResponse(List<UserDetails> userDetailsList, String userName) {
		List<UserDetailsResponse> userDetailsResponseList = userDetailsList.stream().map(x -> modelMapper.map(x, UserDetailsResponse.class)).collect(Collectors.toList());
		NamewiseUserDetailsResponse namewiseUserDetailsResponse = new NamewiseUserDetailsResponse(userName, userDetailsResponseList);
		return namewiseUserDetailsResponse;
	}

//	--------------------1. SELECT QUERY METHODS----------------------------------------------------------------------------------------
	public UserDetailsStateResponse selectById(String userId) throws InvalidIdException {
		UserDetails userDetails = userDetailsRepository.findByUserId(userId);
		if(userDetails != null) {
			UserDetailsStateResponse userDetailsResponse = mapToUserDetailsStateResponse(userDetails);
			return userDetailsResponse;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(userId));
		}
	}
	
	public StatewiseUserDetailsResponse selectUserDetailsByStateId(String stateId) throws  InvalidIdException{
		StatesResponse statesResponse = userDetailsStatesFeign.selectById(stateId);
		if(statesResponse != null) {
			//Actual user details
			List<UserDetails> userDetailsList = userDetailsRepository.findByStateId(stateId);
			return mapToStatewiseUserDetailsResponse(userDetailsList, statesResponse);
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage("There is not state with state id: "+stateId));
		}
	}
	
	public NamewiseUserDetailsResponse selectUserDetailsByUserName(String userName) throws  InvalidNameException{
		if(validation.isValidName(userName)) {
			List<UserDetails> userDetailsList = userDetailsRepository.findByUserName(userName);
			return mapToNamewiseUserDetailsResponse(userDetailsList, userName);
		}else {
			throw new InvalidNameException(exceptionMessages.getInvalidNameExceptionMessage(userName));
		}
	}
	
	public UserDetails getUserDetailsByEmailAndPassword(String email, String password) {
		return userDetailsRepository.findUserByEmaildIdAndPassword(email, password);
	}
	
	public List<UserDetails> getAllUsers() {
		return userDetailsRepository.findAll();
	}
	
//	--------------------2. INSERT QUERY METHODS-----------------------------------------------------------------------------------------
	public String insertDetails(UserDetails obj, String stateId) throws InvalidDetailsException {
		UserDetails userDetails = userDetailsRepository.findUserByEmailId(obj.getUserEmail());
		if(userDetails == null) {
			obj.setStateId(stateId);
			obj.setUserId(generateUserId(stateId));
			userDetailsRepository.save(obj);
			return "Successfully added user details with user id: "+obj.getUserId();
		}else {
			throw new InvalidDetailsException("Already user existed with email "+obj.getUserEmail() );
		}
	}
	
//	---------------------3. UPDATE QUERY METHODS-----------------------------------------------------------------------------------------
	public String updateName(String userId, String name) throws InvalidIdException, InvalidNameException {
		UserDetails userDetails = userDetailsRepository.findByUserId(userId);
		if(userDetails != null) {
			if(validation.isValidName(name)) {
				String oldName = userDetailsRepository.findUserNameByUserId(userId);
				userDetails.setUserName(name);
				userDetailsRepository.save(userDetails);
				return "Successfully updated the name of user from "+oldName+" to "+name;
			}else {
				throw new InvalidNameException(exceptionMessages.getInvalidNameExceptionMessage(name));
			}
		}else{
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(userId));
		}
	}
	
	public String updatePhone(String userId, String newPhone) throws InvalidIdException, InvalidPhoneNumberException{
		UserDetails userDetails = userDetailsRepository.findByUserId(userId);
		if(userDetails != null) {
			if(validation.isValidPhoneNumber(newPhone)) {
				String oldPhone = userDetailsRepository.findUserPhoneByUserId(userId);
				userDetails.setUserPhone(newPhone);
				userDetailsRepository.save(userDetails);
				return "Successfully updated the phone number of user from "+oldPhone+" to "+newPhone;
			}else {
				throw new InvalidPhoneNumberException(exceptionMessages.getInvalidPhoneNumberExceptionMessage());
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(userId));
		}
	}
	
	public String updateEmail(String userId, String newEmail) throws InvalidIdException, InvalidEmailIdException{
		UserDetails userDetails = userDetailsRepository.findByUserId(userId);
		if(userDetails != null) {
			if(validation.isValidEmail(newEmail)) {
				String oldEmail = userDetailsRepository.findUserEmailByUserId(userId);
				userDetails.setUserEmail(newEmail);
				userDetailsRepository.save(userDetails);
				return "Successfully updated the email address of user from "+oldEmail+" to "+newEmail;
			}else {
				throw new InvalidEmailIdException(exceptionMessages.getInvalidEmailIdExceptionMessage());
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(userId));
		}
	}
	
	public String updatePassword(String userId, String oldPassword, String newPassword) throws InvalidIdException, InvalidPasswordException{
		UserDetails userDetails = userDetailsRepository.findByUserId(userId);
		if(userDetails != null) {
			if(oldPassword.equals(userDetails.getPassword())) {
				if(validation.isValidPassword(newPassword)) {
					userDetails.setPassword(newPassword);
					userDetailsRepository.save(userDetails);
					return "Successfully update the password of user "+userDetails.getUserId();
				}else {
					throw new InvalidPasswordException(exceptionMessages.getInvalidPasswordExceptionMessageForNewPasswordVerification());
				}
			}else {
				throw new InvalidPasswordException(exceptionMessages.getInvalidPasswordExceptionMessageForOldPasswordVerification());
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(userId));
		}
	}
	
//	---------------------4. DELETE QUERY METHODS-----------------------------------------------------------------------------------------
	public String deleteById(String id) throws InvalidIdException {
		UserDetails userDetails = userDetailsRepository.findByUserId(id);
		if(userDetails != null) {
			userDetailsRepository.deleteById(id);
			return "Successfully deleted the user details of "+id;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(id));
		}
	}


	
}
