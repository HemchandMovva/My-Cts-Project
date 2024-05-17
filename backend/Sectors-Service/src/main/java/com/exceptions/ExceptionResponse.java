package com.exceptions;

import java.time.LocalDate;

public class ExceptionResponse {

	private LocalDate timeStamp;
	private String message;
	private String details;
	private String httpCodeMessage;
	
	

	public ExceptionResponse(LocalDate timeStamp, String message, String details, String httpCodeMessage) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
		this.httpCodeMessage = httpCodeMessage;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public String getHttpCodeMessage() {
		return httpCodeMessage;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setHttpCodeMessage(String httpCodeMessage) {
		this.httpCodeMessage = httpCodeMessage;
	}

}
