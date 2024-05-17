package com.exceptions;

@SuppressWarnings("serial")
public class InvalidEmailIdException extends Exception{

	public InvalidEmailIdException(String str) {
		super(str);
	}
}
