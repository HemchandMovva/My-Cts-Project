package com.exceptions;


@SuppressWarnings("serial")
public class InvalidDetailsException extends Exception{

	public InvalidDetailsException(String str) {
		super(str);
	}
}
