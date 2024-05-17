package com.exceptions;

import org.springframework.stereotype.Component;

import com.entities.Sectors;




@Component
public class ExceptionMessages {

	
//	-----------------------------NEW WAY OF GETTING EXCEPTION MESSAGES---------------------------------------------------------------------------------------------------------------
	
//	public String getInvalidIdExceptionMessage(String id) {
//		return "There is no ngo with ngo id: "+id;
//	}
//	
	public String getInvalidDetailsExceptionMessage(Sectors obj) {
		return "There is an sector with sector name: "+obj.getSectorName();
	}
	
	
//	--------------------------MISCELLANIOUS EXCEPTIONS------------------------------------------------------------------------------------------------
	
	public String getInvalidEmailIdExceptionMessage() {
		return "Please provide valid email address";
	}
	
	public String getInvalidPasswordExceptionMessageForOldPasswordVerification() {
		return "The old password you given is not correct please try again";
	}
	
	public String getInvalidPasswordExceptionMessageForNewPasswordVerification() {
		return "The new password  /n 1. must contain length length at least 8 characters and maximum of 20 characters /n 2. must contain at least one special character /n 3. must contain at least one uppercase Latin character /n 4. must contain at least one lowercase Latin character /n 5. must contain at least one digit";
	}
	
	public String getInvalidPhoneNumberExceptionMessage() {
		return "The given phone number must contain length 10 & it must starts with digit 6 or 7 or 8 or 9";
	}
	
	public String getInvalidNameExceptionMessage(String name) {
		return "The name should not be empty | null, but you given "+name;
	}
	
	public String getInvalidFundExceptionMessage() {
		return "The fund must be greater than 0";
	}

	public String getInvalidNgoCountExceptionMessage() {
		return "The given ngo count must be greater than zero";
	}
	
//	----------------------------STATES DAO IMPL EXCEPTIONS--------------------------------------------------------------------------------------------------------------------------
	

	
//	----------------------------ADMIN DETAIL DAO EXCEPTIONS------------------------------------------------------------------------------------------------------------------------
	
	
	
//	----------------------------USER DETAILS DAO EXCEPTIONS-----------------------------------------------------------------------------------------------------------------------
	
	
	
	
//	----------------------------NGO DETAILS DAO EXCEPTIONS---------------------------------------------------------------------------------------------------------------------
	

}
