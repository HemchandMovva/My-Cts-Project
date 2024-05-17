package com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.NgoDetailsDAOImpl;
import com.entities.NgoDetails;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.response.NgoDetailsResponse;
import com.response.NgoDetailsStatesResponse;

@RestController
@RequestMapping("/NgoDetailsControllerImpl")
@CrossOrigin(origins = "*")
public class NgoDetailsControllerImpl {

	@Autowired
	NgoDetailsDAOImpl ngoDetailsDaoImpl;

	//---------------------------------------1. GET MAPPING -------------------------------------------
	@GetMapping("/selectById/{id}")
	public NgoDetailsStatesResponse selectById(@PathVariable String id) throws InvalidIdException {
		return ngoDetailsDaoImpl.selectById(id);
	}
	
	@GetMapping("/selectByStateId/{stateId}")
	public List<NgoDetailsResponse> selectByStateId(@PathVariable String stateId) throws InvalidIdException{
		return ngoDetailsDaoImpl.selectByStateId(stateId);
	}
	
	@GetMapping("/selectAll")
	public List<NgoDetails> selectAll() {
		return ngoDetailsDaoImpl.selectAll();
	}
	
	
	
	//method to be added for getting the ngo's list based on sectors
	
	//---------------------------------------2. POST MAPPING ---------------------------------------------
	@PostMapping("/insertDetails/stateId/{id}")
	public String insertDetails(@RequestBody NgoDetails obj, @PathVariable String id) throws InvalidDetailsException, InvalidIdException {
		return ngoDetailsDaoImpl.insertDetails(obj, id);
	}
	
	//---------------------------------------3. PUT MAPPING -----------------------------------------------
	@PutMapping("/updateName/ngoId/{ngoId}/name/{name}")
	public String updateName(@PathVariable String ngoId, @PathVariable String name) throws InvalidIdException, InvalidNameException {
		return ngoDetailsDaoImpl.updateName(ngoId, name);
	}
	
	@PutMapping("/updateCityAddress/ngoId/{ngoId}/city/{city}/address/{address}")
	public String updateCityAddress(@PathVariable String ngoId, @PathVariable String city, @PathVariable String address) throws InvalidIdException{
		return ngoDetailsDaoImpl.updateCityAddress(ngoId, city, address);
	}
	
	@PutMapping("/updateFund/ngoId/{ngoId}/fund/{fund}")
	public String updateFund(@PathVariable String ngoId, @PathVariable double fund) throws InvalidIdException{
		return ngoDetailsDaoImpl.updateFund(ngoId, fund);
	}

	//---------------------------------------4. DELETE MAPPING ----------------------------------------------
	@DeleteMapping("/deleteById/{ngoId}")
	public String deleteById(@PathVariable String ngoId) throws InvalidIdException {
		return ngoDetailsDaoImpl.deleteById(ngoId);
	}
}
