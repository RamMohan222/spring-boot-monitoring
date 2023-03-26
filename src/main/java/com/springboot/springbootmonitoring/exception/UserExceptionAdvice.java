package com.springboot.springbootmonitoring.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.springbootmonitoring.exception.GlobalExceptionAdvice.Error;

@RestControllerAdvice
public class UserExceptionAdvice {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Error> handleError(RecordNotFoundException e) {
		return ResponseEntity.internalServerError().body(new Error(e.getLocalizedMessage(), new Date()));
	}
}
