package com.uol.desafio.exceptions;

public class ApiRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ApiRequestException(String msg) {
		super (msg);
	}

	public ApiRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
