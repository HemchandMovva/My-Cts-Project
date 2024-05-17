package com.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.entities.States;
import com.exceptions.ExceptionMessages;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidFundException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNgoCountException;
import com.repository.StatesRepository;
import com.response.StatesResponse;

@Service
@ComponentScan("com.configuration")
public class StatesDAOImpl {

	@Autowired
	private StatesRepository statesRepository;
	
	@Autowired
	private ExceptionMessages exceptionMessages;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	--------------------1. SELECT QUERY METHODS----------------------------------------------------------------------------------------
	public StatesResponse selectById(String stateId) throws InvalidIdException{
		States states = statesRepository.findByStateId(stateId);
		if(states != null) {
			StatesResponse statesResponse = modelMapper.map(states, StatesResponse.class);
			return statesResponse;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(stateId));
		}
	}
	
	public List<StatesResponse> getByReceivedFundInRupeesGreaterThanEqual(double receivedFund) throws InvalidFundException{
		if(receivedFund>=0) {
			List<States> states = statesRepository.findByReceivedFundInRupeesGreaterThanEqual(receivedFund);
			List<StatesResponse> statesResponseList = states.stream().map(x -> modelMapper.map(x, StatesResponse.class)).collect(Collectors.toList());
			return statesResponseList;	
		}else {
			throw new InvalidFundException(exceptionMessages.getInvalidFundExceptionMessage());
		}
	}
	
	public List<StatesResponse> getByNgoCountLessThanEqual(int ngoCount) throws InvalidNgoCountException {
		if(ngoCount>=0) {
			List<States> states = statesRepository.findByNgoCountLessThanEqual(ngoCount);
			List<StatesResponse> statesResponseList = states.stream().map(x -> modelMapper.map(x, StatesResponse.class)).collect(Collectors.toList());
			return statesResponseList;
		}else {
			throw new InvalidNgoCountException(exceptionMessages.getInvalidFundExceptionMessage());
		}
	}
	
	public List<StatesResponse> getByNgoCountGreaterThanEqual(int ngoCount) throws InvalidNgoCountException  {
		if(ngoCount>=0) {
			List<States> states = statesRepository.findByNgoCountGreaterThanEqual(ngoCount);
			List<StatesResponse> statesResponseList = states.stream().map(x -> modelMapper.map(x, StatesResponse.class)).collect(Collectors.toList());
			return statesResponseList;
		}else {
			throw new InvalidNgoCountException(exceptionMessages.getInvalidFundExceptionMessage());
		}
	}
	
//	--------------------2. INSERT QUERY METHODS-----------------------------------------------------------------------------------------
	public String insertDetails(States states) throws InvalidDetailsException{
		States state = statesRepository.findByStateId(states.getStateId());
		if(state == null) {
			statesRepository.save(states);
			return "Successfully added states details with id: "+states.getStateId();
		}else {
			throw new InvalidDetailsException(exceptionMessages.getInvalidDetailsExceptionMessage(states));
		}
	}
	
//	---------------------3. UPDATE QUERY METHODS-----------------------------------------------------------------------------------------
	public String updateName(String stateId, String name) throws InvalidIdException{
		States states = statesRepository.findByStateId(stateId);
		if(states != null) {
			String oldStateName = states.getStateName();
			states.setStateName(name);
			statesRepository.save(states);
			return "Successfully updated the "+oldStateName+" to "+name;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(stateId));
		}
	}
	
	public String increaseNgoCountByOne(String stateId) throws InvalidIdException {
		States state = statesRepository.findByStateId(stateId);
		if(state != null) {
			state.setNgoCount(state.getNgoCount()+1);
			statesRepository.save(state);
			return "Successfully increased the ngo count of "+state.getStateName()+" by one and updated ngo_count="+statesRepository.findNgoCountByStateId(stateId);
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(stateId));
		}
	}
	
	public String decreaseNgoCountByOne(String stateId) throws InvalidIdException {
		States state = statesRepository.findByStateId(stateId);
		if(state != null) {
			state.setNgoCount(state.getNgoCount()-1);
			statesRepository.save(state);
			return "Successfully decreased the ngo count of "+state.getStateName()+" by one and updated ngo_count="+statesRepository.findNgoCountByStateId(stateId);
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(stateId));
		}
	}
	
	public String updateReceivedFundInRupeesByStateId(String stateId, double fundToBeAdded) throws InvalidIdException {
		States state = statesRepository.findByStateId(stateId);
		if(state != null) {
			state.setReceivedFundInRupees(state.getReceivedFundInRupees()+fundToBeAdded);
			statesRepository.save(state);
			return "Successfully added the fund to the state "+state.getStateName()+". Updated fund details: receivedFundInRupees="+statesRepository.findReceivedFundInRupeesByStateId(stateId);
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(stateId));
		}
	}
	
//	---------------------4. DELETE QUERY METHODS-----------------------------------------------------------------------------------------
	public String deleteById(String stateId) throws InvalidIdException {
		States states = statesRepository.findByStateId(stateId);
		if(states != null) {
			statesRepository.deleteById(states.getStateId());
			return "Succesffully deleted the states details with state id: "+stateId;
		}else {
			throw new InvalidIdException(exceptionMessages.getInvalidIdExceptionMessage(stateId));
		}
	}
}
