package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dao.StatesDAOImpl;
import com.entities.States;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidFundException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNgoCountException;
import com.response.StatesResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/StatesControllerImpl")
@CrossOrigin(origins = "*")
public class StatesControllerImpl {

	@Autowired
	private StatesDAOImpl statesDaoImpl;
	
//	---------------------------------------1. GET MAPPING -------------------------------------------
	@GetMapping("/selectById/{stateId}")
	@ResponseStatus(HttpStatus.OK)
	public StatesResponse selectById(@Valid @PathVariable String stateId) throws InvalidIdException{
		return statesDaoImpl.selectById(stateId);
	}
	
	@GetMapping(value = "/getByReceivedFundInRupeesGreaterThanEqual/{receivedFund}")
	@ResponseStatus(HttpStatus.OK)
	public List<StatesResponse> getByReceivedFundInRupeesGreaterThanEqual(@Valid @PathVariable double receivedFund) throws InvalidFundException{
		return statesDaoImpl.getByReceivedFundInRupeesGreaterThanEqual(receivedFund);
	}
	
	@GetMapping(value ="/getByNgoCountLessThanEqual/{ngoCount}")
	@ResponseStatus(HttpStatus.OK)
	public List<StatesResponse> getByNgoCountLessThanEqual(@Valid @PathVariable int ngoCount) throws InvalidNgoCountException  {
		return statesDaoImpl.getByNgoCountLessThanEqual(ngoCount);
	}
	
	@GetMapping(value = "/getByNgoCountGreaterThanEqual/{ngoCount}")
	@ResponseStatus(HttpStatus.OK)
	public List<StatesResponse> getByNgoCountGreaterThanEqual(@Valid @PathVariable int ngoCount) throws InvalidNgoCountException  {
		return statesDaoImpl.getByNgoCountGreaterThanEqual(ngoCount);
	}
	
//	------------------------------------2. POST MAPPING ---------------------------------------------
	@PostMapping("/insertDetails")
	@ResponseStatus(HttpStatus.CREATED)
	public String insertDetails(@Valid @RequestBody States states) throws InvalidDetailsException{
		return statesDaoImpl.insertDetails(states);
	}
	
//	------------------------------------3. PUT MAPPING -----------------------------------------------
	@PutMapping(value = "/updateName/stateId/{stateId}/name/{name}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateName(@Valid @PathVariable String stateId, @Valid @PathVariable String name)  throws InvalidIdException{
		return statesDaoImpl.updateName(stateId, name);
	}
	
	@PutMapping(value = "/increaseNgoCountByOne/{stateId}")
	@ResponseStatus(HttpStatus.CREATED)
	public String increaseNgoCountByOne(@Valid @PathVariable String stateId) throws InvalidIdException {
		return statesDaoImpl.increaseNgoCountByOne(stateId);
	}
	
	@PutMapping(value = "/decreaseNgoCountByOne/{stateId}")
	@ResponseStatus(HttpStatus.CREATED)
	public String decreaseNgoCountByOne(@Valid @PathVariable String stateId) throws InvalidIdException {
		return statesDaoImpl.decreaseNgoCountByOne(stateId);
	}
	
	@PutMapping(value = "/updateReceivedFundInRupeesByStateId/stateId/{stateId}/fundToBeAdded/{fundToBeAdded}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateReceivedFundInRupeesByStateId(@Valid @PathVariable String stateId, @Valid @PathVariable double fundToBeAdded) throws InvalidIdException {
		return statesDaoImpl.updateReceivedFundInRupeesByStateId(stateId, fundToBeAdded);
	}
	
//	-----------------------------------4. DELETE MAPPING ----------------------------------------------
	@DeleteMapping("/deleteById/{stateId}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteById(@Valid @PathVariable String stateId) throws InvalidIdException {
		return statesDaoImpl.deleteById(stateId);
	}
}
