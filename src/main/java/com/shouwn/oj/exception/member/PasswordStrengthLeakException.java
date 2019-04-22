package com.shouwn.oj.exception.member;

public class PasswordStrengthLeakException extends MemberException {

	public PasswordStrengthLeakException(String message) {
		super(message);
	}

	public PasswordStrengthLeakException(String message, Throwable cause) {
		super(message, cause);
	}
}
