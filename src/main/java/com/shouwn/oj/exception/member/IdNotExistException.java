package com.shouwn.oj.exception.member;

public class IdNotExistException extends MemberException {

	public IdNotExistException(String message) {
		super(message);
	}

	public IdNotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
