package com.exceptions;

@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception{
	
	public InvalidPasswordException(String str) {
		super(str);
	}
}
