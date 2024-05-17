package com.controller;

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

import com.dao.SectorsDAOImpl;
import com.entities.Sectors;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.response.NgoDetailsSectorsResponse;
import com.response.SectorsNgoDetailsResponse;

@RestController
@RequestMapping("/SectorsControllerImpl")
@CrossOrigin(origins = "*")
public class SectorsControllerImpl {

	@Autowired
	SectorsDAOImpl sectorsDaoImpl;
	
	
	//---------------------------------------1. GET MAPPING -------------------------------------------
	@GetMapping("/selectByNgoId/{ngoId}")
	public NgoDetailsSectorsResponse selectByNgoId(@PathVariable String ngoId) throws InvalidIdException{
		return sectorsDaoImpl.selectByNgoId(ngoId);
	}
	
	@GetMapping("/selectNgosBySectorName/{sectorName}")
	public SectorsNgoDetailsResponse selectNgosBySectorName(@PathVariable String sectorName) throws InvalidIdException{
		return sectorsDaoImpl.selectNgosBySectorName(sectorName);
	}

	//---------------------------------------2. POST MAPPING ---------------------------------------------

	@PostMapping("/insertDetails/ngoId/{ngoId}")
	public String insertDetails(@RequestBody Sectors obj, @PathVariable String ngoId) throws InvalidDetailsException, InvalidIdException {
		return sectorsDaoImpl.insertDetails(obj, ngoId);
	}

	//---------------------------------------3. PUT MAPPING -----------------------------------------------

	
	@PutMapping("/updateName/oldName/{oldName}/newName/{newName}/ngoId/{ngoId}")
	public String updateName(@PathVariable String oldName, @PathVariable String newName, @PathVariable String ngoId) throws InvalidIdException, InvalidNameException {
		return sectorsDaoImpl.updateName(oldName, newName, ngoId);
	}

	//---------------------------------------4. DELETE MAPPING ----------------------------------------------
	
	@DeleteMapping("/deleteById/ngoId/{ngoId}/sectorName/{sectorName}")
	public String deleteById(@PathVariable String ngoId, @PathVariable String sectorName) throws InvalidIdException {
		return sectorsDaoImpl.deleteById(ngoId, sectorName);
	}
}
