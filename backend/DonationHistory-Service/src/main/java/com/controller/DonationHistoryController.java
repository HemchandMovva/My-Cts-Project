package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.DonationHistoryDAOImpl;
import com.entities.DonationHistory;
import com.exceptions.InvalidIdException;
import com.response.DonationHistoryResponse;

@RestController
@RequestMapping("/DonationHistoryControllerImpl")
@CrossOrigin(origins = "*")
public class DonationHistoryController {

	@Autowired
	DonationHistoryDAOImpl donationHistoryDaoImpl;

	//---------------------------------------1. GET MAPPING -------------------------------------------

	@GetMapping("/selectById/{donationId}")
	public DonationHistoryResponse selectById(@PathVariable String donationId) throws InvalidIdException{
		return donationHistoryDaoImpl.selectById(donationId);
	}
	
	@GetMapping("/selectByUserId/{userId}")
	public List<DonationHistory> selectByUserId(@PathVariable String userId) {
		return donationHistoryDaoImpl.selectByUserId(userId);
	}
	
	//---------------------------------------2. POST MAPPING ---------------------------------------------
	@PostMapping("/insertDetails/userId/{userId}")
	public String insertDetails(@RequestBody DonationHistory donationHistory, @PathVariable String userId) throws InvalidIdException {
		return donationHistoryDaoImpl.insertDetails(donationHistory, userId);
//		return "added";
	}
}
