package com.shouwn.oj.exception.member;

public class PasswordIncorrectException extends MemberException {

	public PasswordIncorrectException(String message) {
		super(message);
	}

	public PasswordIncorrectException(String message, Throwable cause) {
		super(message, cause);
	}
}
