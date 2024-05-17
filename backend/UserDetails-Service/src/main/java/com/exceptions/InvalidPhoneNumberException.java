package com.exceptions;

@SuppressWarnings("serial")
public class InvalidPhoneNumberException extends Exception{

	public InvalidPhoneNumberException(String str) {
		super(str);
	}
}
