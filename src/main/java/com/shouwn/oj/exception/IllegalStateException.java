package com.shouwn.oj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class IllegalStateException extends OJException {

	public IllegalStateException(String message) {
		super(message);
	}

	public IllegalStateException(String message, Throwable cause) {
		super(message, cause);
	}
}
