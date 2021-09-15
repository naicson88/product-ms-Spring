package com.uol.desafio.exceptions;


public class ErrorMessage {
	
	private String status_code;
	private String message;
	
	public ErrorMessage() {
		
	}

	public ErrorMessage(String status_code, String message) {
		super();
		this.status_code = status_code;
		this.message = message;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
