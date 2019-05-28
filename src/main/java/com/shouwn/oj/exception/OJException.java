package com.shouwn.oj.exception;

public class OJException extends RuntimeException {

	public OJException(String message) {
		super(message);
	}

	public OJException(String message, Throwable cause) {
		super(message, cause);
	}
}
