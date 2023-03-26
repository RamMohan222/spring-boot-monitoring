package com.springboot.springbootmonitoring.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

	record Error(String message, Date time) {
		public Error(String message) {
			this(message, null);
		}
	}

	//@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleError(Exception e) {
		return ResponseEntity.internalServerError().body(new Error(e.getLocalizedMessage()));
	}
}
