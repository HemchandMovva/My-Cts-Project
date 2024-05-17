package com.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entities.States;

@Component
public class ExceptionMessages {
	
//	-----------------------------NEW WAY OF GETTING EXCEPTION MESSAGES---------------------------------------------------------------------------------------------------------------
	public String getInvalidIdExceptionMessage(String id) {
		return "There is no state with state id: "+id;
	}
	
	public String getInvalidDetailsExceptionMessage(States states) {
		return "There is a state with state id: "+states.getStateId();
	}
	
//	--------------------------MISCELLANIOUS EXCEPTIONS------------------------------------------------------------------------------------------------
	public String getInvalidFundExceptionMessage() {
		return "The fund must be greater than 0";
	}

	public String getInvalidNgoCountExceptionMessage() {
		return "The given ngo count must be greater than zero";
	}
	
	public String getInvalidNameExceptionMessage(String name) {
		return "The name should not be empty | null, but you given "+name;
	}
}
