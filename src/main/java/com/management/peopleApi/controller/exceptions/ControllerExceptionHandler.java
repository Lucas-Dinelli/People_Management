package com.management.peopleApi.controller.exceptions;

import java.time.LocalDate;

import javax.servlet.ServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.management.peopleApi.service.exceptions.DataIntegrityViolationCustomException;
import com.management.peopleApi.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException e, ServletRequest request){
		ValidationError error = new ValidationError(LocalDate.now(), HttpStatus.BAD_REQUEST.value(), "Error in field validation.");
		
		for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.addFieldValidation(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<DefaultError> objectNotFoundException(ObjectNotFoundException e, ServletRequest request){
		DefaultError error = new DefaultError(LocalDate.now(), HttpStatus.NOT_FOUND.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	@ExceptionHandler(DataIntegrityViolationCustomException.class)
	public ResponseEntity<DefaultError> dataIntegrityViolationException(DataIntegrityViolationCustomException e, ServletRequest request){
		DefaultError error = new DefaultError(LocalDate.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
