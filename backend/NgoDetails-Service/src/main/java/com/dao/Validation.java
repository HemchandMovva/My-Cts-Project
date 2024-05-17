package com.dao;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;



@Component
public class Validation {

	public boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		String regex_pattern = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex_pattern);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public boolean isValidPhoneNumber(String newPhoneNumber) {
		// TODO Auto-generated method stub
		if(newPhoneNumber.length()==10 & (newPhoneNumber.startsWith("6") | newPhoneNumber.startsWith("7") | newPhoneNumber.startsWith("8") | newPhoneNumber.startsWith("9"))){
			return true;
		}
		return false;
	}
	
	public boolean isValidPassword(String newPassword) {
		// TODO Auto-generated method stub
		String regex_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
		Pattern pattern = Pattern.compile(regex_pattern);
		Matcher matcher = pattern.matcher(newPassword);
		if(matcher.matches()) {
			return true;
		}
		return false;
	}

	public boolean isValidName(String newName) {
		// TODO Auto-generated method stub
		
		if(newName.isEmpty() | newName == null | newName.isBlank()) {
			return false;
		}
		return true;
	}

	
}
