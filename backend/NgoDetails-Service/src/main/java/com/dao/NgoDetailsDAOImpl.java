package com.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.entities.NgoDetails;
import com.exceptions.ExceptionMessages;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.feign.NgoDetailsStatesFeign;
import com.repository.NgoDetailsRepository;
import com.response.NgoDetailsResponse;
import com.response.NgoDetailsStatesResponse;
import com.response.StatesResponse;

@Component
@ComponentScan("com.configuration")
public class NgoDetailsDAOImpl {
	
	@Autowired
	private NgoDetailsRepository ngoDetailsRepository;
	
	@Autowired
	private NgoDetailsStatesFeign ngoDetailsStatesFeign;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ExceptionMessages exceptionMessages;
	
	@Autowired
	private Validation validation;

	// Generates the ngoId
	public String generateNgoId(String stateId, int cnt) {
		return "NGO"+"-"+stateId+"-"+String.format("%05d", cnt);
	}
	// Getting the buffered reader object
	public BufferedReader getBufferedReader(String filePath) throws java.io.FileNotFoundException  {
		return new BufferedReader(new FileReader(filePath));
	}
	
//	-------------------MAPPING TO RESPONSE CLASSES------------------------------------------------------------------------------------
	
	public NgoDetailsStatesResponse mapToNgoDetailsStateresponse(NgoDetails ngoDetails) throws InvalidIdException {
		StatesResponse statesResponse = ngoDetailsStatesFeign.selectById(ngoDetails.getStateId());
		NgoDetailsStatesResponse ngoDetailsStatesResponse = modelMapper.map(ngoDetails, NgoDetailsStatesResponse.class);
		ngoDetailsStatesResponse.setStatesResponse(statesResponse);
		return ngoDetailsStatesResponse;
	}
	
	public List<NgoDetailsResponse> mapToNgoDetailsResponse(List<NgoDetails> ngoDetailsList){
		List<NgoDetailsResponse> ngoDetailsResponseList = ngoDetailsList.stream().map(x -> modelMapper.map(x, NgoDetailsResponse.class)).collect(Collectors.toList());
		return ngoDetailsResponseList;
	}

	
//	--------------------1. SELECT QUERY METHODS----------------------------------------------------------------------------------------
	public NgoDetailsStatesResponse selectById(String id) throws InvalidIdException {
		NgoDetails ngoDetails = ngoDetailsRepository.findByNgoId(id);
		if(ngoDetails != null) {
			return mapToNgoDetailsStateresponse(ngoDetails);
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(id));
		}
	}
	
	public List<NgoDetails> selectAll() {
		List<NgoDetails> ngoDetails = ngoDetailsRepository.findAll();
		return ngoDetails;
	}
	
	public List<NgoDetailsResponse> selectByStateId(String stateId) throws InvalidIdException{
		
		StatesResponse statesResponse = ngoDetailsStatesFeign.selectById(stateId);

		if(statesResponse != null) {
			List<NgoDetails> ngoDetailsList = ngoDetailsRepository.findByStateId(stateId);
			return mapToNgoDetailsResponse(ngoDetailsList);
			
		}else {
			throw new InvalidIdException("There is no state with state id: "+stateId);
		}
		
	}
	
//	--------------------2. INSERT QUERY METHODS-----------------------------------------------------------------------------------------
	public String insertDetails(NgoDetails obj, String stateId) throws InvalidDetailsException, InvalidIdException {
		NgoDetails ngoDetailsExisted = ngoDetailsRepository.findByNgoId(obj.getNgoId());
		String ngoName = obj.getNgoName();
		List<NgoDetails> lis = ngoDetailsRepository.findByStateId(stateId);
		boolean check=true;
		for(NgoDetails details: lis) {
			if(details.getNgoName().equals(ngoName)) {
				check=false;
				break;
			}
		}
		if(ngoDetailsExisted == null && check) {
			StatesResponse statesResponse = ngoDetailsStatesFeign.selectById(stateId);
			//Set stateId
			obj.setStateId(stateId);
			//Auto generating the ngoId
			int ngoCount = ngoDetailsRepository.findCountByStateId(stateId)+1;
			String ngoId = generateNgoId(stateId, ngoCount);
			obj.setNgoId(ngoId);
			//Save
			ngoDetailsRepository.save(obj);
			//UPDATING THE NGO COUNT
			ngoDetailsStatesFeign.increaseNgoCountByOne(stateId);
			return "Succesffully added ngo details with ngoId: "+obj.getNgoId()+" in "+statesResponse.getStateName();
				
		}else {
			throw new InvalidDetailsException("There is an ngo with ngo name "+ngoName);
		}
	}
	
	public void insertRawNgoDetails(String filePath, String stateId) throws IOException, InvalidIdException {
		StatesResponse statesResponse = ngoDetailsStatesFeign.selectById(stateId);
		if(statesResponse != null) {
			BufferedReader bufferedReader = getBufferedReader(filePath);
			String data;
			int count = 1;
			while((data = bufferedReader.readLine()) != null) {
				String[] rawNgoData = data.split(":");
				String ngoName = rawNgoData[1];
				String ngoCity = rawNgoData[3];
				String ngoAddress = rawNgoData[4];
				String ngoId = generateNgoId(stateId, count);
				//Object creation
				NgoDetails ngoDetails = new NgoDetails(ngoId, ngoName, ngoCity, ngoAddress, 0, stateId);
				try {
					insertDetails(ngoDetails, stateId);
					count+=1;
				}catch(Exception e) {
					System.err.println(e);
					break;
				}
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage("There is no state with state id: "+stateId));
		}
	}
	
//	---------------------3. UPDATE QUERY METHODS-----------------------------------------------------------------------------------------
	public String updateName(String ngoId, String name) throws InvalidIdException, InvalidNameException {
		NgoDetails ngoDetails = ngoDetailsRepository.findByNgoId(ngoId);
		if(ngoDetails != null) {
			if(validation.isValidName(name)) {
				String oldName = ngoDetailsRepository.findNgoNameByNgoId(ngoId);
				ngoDetails.setNgoName(name);
				ngoDetailsRepository.save(ngoDetails);
				return "Successfully updated the ngo name from "+oldName+" to "+name;
			}else {
				throw new InvalidNameException(exceptionMessages.getInvalidNameExceptionMessage(name));
			}
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(ngoId));
		}
	}
	
	public String updateCityAddress(String ngoId, String city, String address) throws InvalidIdException{
		NgoDetails ngoDetails = ngoDetailsRepository.findByNgoId(ngoId);
		if(ngoDetails != null) {
			ngoDetails.setCity(city);
			ngoDetails.setAddress(address);
			ngoDetailsRepository.save(ngoDetails);
			return "Successfully updated the city name and address";
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(ngoId));
		}
	}
	
	public String updateFund(String ngoId, double fund) throws InvalidIdException{
		NgoDetails ngoDetails = ngoDetailsRepository.findByNgoId(ngoId);
		if(ngoDetails != null) {
			ngoDetails.setReceivedFundInRupees(fund+ngoDetails.getReceivedFundInRupees());
			ngoDetailsRepository.save(ngoDetails);
			String stateId = ngoDetails.getStateId();
			//UPDATING THE FUND OF STATE AT THE SAME TIME
			ngoDetailsStatesFeign.updateReceivedFundInRupeesByStateId(stateId, fund);
			return "Fund details updated successfully";
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(ngoId));
		}
	}
	
	
	
//	---------------------4. DELETE QUERY METHODS-----------------------------------------------------------------------------------------
	public String deleteById(String ngoId) throws InvalidIdException {
		NgoDetails ngoDetails = ngoDetailsRepository.findByNgoId(ngoId);
		if(ngoDetails != null) {
			ngoDetailsRepository.deleteByNgoId(ngoDetails.getNgoId());	
			//DECREASING THE NGO COUNT BY ONE
			ngoDetailsStatesFeign.decreaseNgoCountByOne(ngoDetails.getStateId());
			return "Successfully deleted the ngo details of "+ngoId;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(ngoId));
		}
	}
}

