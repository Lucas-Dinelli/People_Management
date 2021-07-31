package com.management.peopleApi.service.exceptions;

public class DataIntegrityViolationCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityViolationCustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegrityViolationCustomException(String message) {
		super(message);
	}

}
