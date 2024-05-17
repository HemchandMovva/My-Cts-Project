package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.dao.AdminDetailsDAOImpl;
import com.entities.AdminDetails;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidEmailIdException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.exceptions.InvalidPasswordException;
import com.exceptions.InvalidPhoneNumberException;
import com.response.AdminDetailsResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/AdminDetailsControllerImpl")
@CrossOrigin(origins = "*")
public class AdminDetailsControllerImpl {

	@Autowired
	AdminDetailsDAOImpl adminDetailsDaoImpl;
//	---------------------------------------1. GET MAPPING -------------------------------------------
	@GetMapping("/selectById/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public AdminDetailsResponse selectById(@Valid @PathVariable String id) throws InvalidIdException {
		return adminDetailsDaoImpl.selectById(id);
	}
	
//	---------------------------------------2. POST MAPPING ---------------------------------------------
	@PostMapping("/insertDetails/{stateId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String insertDetails(@Valid @RequestBody AdminDetails obj, @Valid @PathVariable String stateId) throws InvalidDetailsException {
		return adminDetailsDaoImpl.insertDetails(obj, stateId);
	}
	
//	---------------------------------------3. PUT MAPPING -----------------------------------------------
	@PutMapping("/adminId/{adminId}/updateName/{name}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String updateName(@PathVariable String adminId, @PathVariable String name) throws InvalidIdException, InvalidNameException {
		return adminDetailsDaoImpl.updateName(adminId, name);
	}
	
	@PutMapping("/updatePhone/adminId/{adminId}/newPhone/{newPhone}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String updatePhone(@PathVariable String adminId, @PathVariable String newPhone) throws InvalidIdException, InvalidPhoneNumberException{
		return adminDetailsDaoImpl.updatePhone(adminId, newPhone);
	}
	
	@PutMapping("/updatePassword/adminId/{adminId}/oldPassword/{oldPassword}/newPassword/{newPassword}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String updatePassword(@PathVariable String adminId, @PathVariable String oldPassword, @PathVariable String newPassword) throws InvalidPasswordException, InvalidIdException{
		return adminDetailsDaoImpl.updatePassword(adminId, oldPassword, newPassword);
	}
	
	@PutMapping("/updateEmail/adminId/{adminId}/newEmailId/{newEmailId}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String updateEmail(@PathVariable String adminId, @PathVariable String newEmailId) throws InvalidEmailIdException, InvalidIdException{
		return adminDetailsDaoImpl.updateEmail(adminId, newEmailId);
	}
	
//	---------------------------------------4. DELETE MAPPING ----------------------------------------------
	@DeleteMapping("/deleteById/{adminId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteById(@PathVariable String adminId) throws InvalidIdException {
		return adminDetailsDaoImpl.deleteById(adminId);
	}
}
