package com.shouwn.oj.exception.member;

public class EmailExistException extends MemberException {

	public EmailExistException(String message) {
		super(message);
	}

	public EmailExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
