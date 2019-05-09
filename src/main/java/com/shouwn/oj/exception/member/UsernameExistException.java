package com.shouwn.oj.exception.member;

public class UsernameExistException extends MemberException {

	public UsernameExistException(String message) {
		super(message);
	}

	public UsernameExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
