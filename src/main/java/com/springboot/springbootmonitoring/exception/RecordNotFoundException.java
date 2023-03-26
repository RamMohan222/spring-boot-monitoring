package com.springboot.springbootmonitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4218682785538276671L;

	public RecordNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
