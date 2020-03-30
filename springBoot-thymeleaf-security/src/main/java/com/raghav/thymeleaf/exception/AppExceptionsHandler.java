package com.raghav.thymeleaf.exception;


import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<Object>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<Object>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request){
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<Object>(errorMessage, HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest request){
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
		return new ResponseEntity<Object>(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
	}

	
	

}
