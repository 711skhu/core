package com.shouwn.oj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationFailedException extends OJException {

	public AuthenticationFailedException(String message) {
		super(message);
	}

	public AuthenticationFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
