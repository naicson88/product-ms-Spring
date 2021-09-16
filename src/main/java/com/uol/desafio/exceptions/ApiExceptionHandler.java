package com.uol.desafio.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<ErrorMessage> handleExceptionError(Exception e) {
		ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
		
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMsg());
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}	
	
	@ExceptionHandler(value = {ApiRequestException.class, IllegalArgumentException.class})
	public ResponseEntity<Object> handleValiationException(ApiRequestException e){
		
		ApiExceptions ex = new ApiExceptions(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));	
		return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {NoSuchElementException.class})
	public ResponseEntity<Object> handleNotFoundlErros(NoSuchElementException e){
		
		ApiExceptions ex = new ApiExceptions(e.getMessage(), e.getCause(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));	
		return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	}
	
	
	
}
