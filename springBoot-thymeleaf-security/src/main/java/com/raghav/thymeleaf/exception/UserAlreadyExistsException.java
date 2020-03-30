package com.raghav.thymeleaf.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -1090554834981809278L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
