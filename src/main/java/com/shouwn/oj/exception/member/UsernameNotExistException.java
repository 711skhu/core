package com.shouwn.oj.exception.member;

public class UsernameNotExistException extends MemberException {

	public UsernameNotExistException(String message) {
		super(message);
	}

	public UsernameNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
