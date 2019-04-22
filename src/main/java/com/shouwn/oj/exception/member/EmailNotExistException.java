package com.shouwn.oj.exception.member;

public class EmailNotExistException extends MemberException {

	public EmailNotExistException(String message) {
		super(message);
	}

	public EmailNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
