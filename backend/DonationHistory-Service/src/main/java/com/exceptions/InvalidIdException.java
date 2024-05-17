package com.exceptions;

@SuppressWarnings("serial")
public class InvalidIdException extends Exception{

	public InvalidIdException(String str) {
		super(str);
	}
}
