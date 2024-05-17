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

import com.dao.UserDetailsDAOImpl;
import com.entities.UserDetails;
import com.exceptions.InvalidDetailsException;
import com.exceptions.InvalidEmailIdException;
import com.exceptions.InvalidIdException;
import com.exceptions.InvalidNameException;
import com.exceptions.InvalidPasswordException;
import com.exceptions.InvalidPhoneNumberException;
import com.response.NamewiseUserDetailsResponse;
import com.response.StatewiseUserDetailsResponse;

@RestController
@RequestMapping("/UserDetailsControllerImpl")
@CrossOrigin(origins = "*")
public class UserDetailsControllerImpl {

	@Autowired
	UserDetailsDAOImpl userDetailsDaoImpl;
	
//	---------------------------------------1. GET MAPPING -------------------------------------------
	@GetMapping(value = "/selectById/{userId}")
	public Object selectById(@PathVariable String userId) throws InvalidIdException {
		return userDetailsDaoImpl.selectById(userId);
	}
	
	@GetMapping(value = "/selectUserDetailsByStateId/{stateId}")
	public StatewiseUserDetailsResponse selectUserDetailsByStateId(@PathVariable String stateId) throws  InvalidIdException{
		return userDetailsDaoImpl.selectUserDetailsByStateId(stateId);
	}
	
	@GetMapping(value = "/selectUserDetailsByUserName/{userName}")
	public NamewiseUserDetailsResponse selectUserDetailsByUserName(@PathVariable String userName) throws  InvalidNameException{
		return userDetailsDaoImpl.selectUserDetailsByUserName(userName);
	}
	
	@GetMapping(value = "/getUserDetailsByEmailAndPassword/email/{email}/password/{password}")
	public UserDetails getUserDetailsByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		return userDetailsDaoImpl.getUserDetailsByEmailAndPassword(email, password);
	}
	
	@GetMapping(value = "/getAllUsers")
	public List<UserDetails> getAllUsers(){
		return userDetailsDaoImpl.getAllUsers();
	}
	
//	---------------------------------------2. POST MAPPING ---------------------------------------------
	
	@PostMapping("/insertDetails/stateId/{stateId}")
	public String insertDetails(@RequestBody UserDetails obj, @PathVariable String stateId) throws InvalidDetailsException {
		return userDetailsDaoImpl.insertDetails(obj, stateId);
	}
	
//	---------------------------------------3. PUT MAPPING -----------------------------------------------
	
	@PutMapping("/updateName/userId/{userId}/name/{name}")
	public String updateName(@PathVariable String userId, @PathVariable String name) throws InvalidIdException, InvalidNameException {
		return userDetailsDaoImpl.updateName(userId, name);
	}
	
	@PutMapping("/updatePhone/userId/{userId}/newPhone/{newPhone}")
	public String updatePhone(@PathVariable String userId, @PathVariable String newPhone) throws InvalidIdException, InvalidPhoneNumberException{
		return userDetailsDaoImpl.updatePhone(userId, newPhone);
	}
	
	@PutMapping("/updateEmail/userId/{userId}/newEmail/{newEmail}")
	public String updateEmail(@PathVariable String userId, @PathVariable String newEmail) throws InvalidIdException, InvalidEmailIdException{
		return userDetailsDaoImpl.updateEmail(userId, newEmail);
	}
	
	@PutMapping("/updatePassword/userId/{userId}/oldPassword/{oldPassword}/newPassword/{newPassword}")
	public String updatePassword(@PathVariable String userId, @PathVariable String oldPassword, @PathVariable String newPassword) throws InvalidIdException, InvalidPasswordException{
		return userDetailsDaoImpl.updatePassword(userId, oldPassword, newPassword);
	}
	
//	---------------------------------------4. DELETE MAPPING ----------------------------------------------
	@DeleteMapping("/deleteById/{userId}")
	public String deleteById(@PathVariable String userId) throws InvalidIdException {
		return userDetailsDaoImpl.deleteById(userId);
	}
}
