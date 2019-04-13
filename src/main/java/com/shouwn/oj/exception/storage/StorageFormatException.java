package com.shouwn.oj.exception.storage;

public class StorageFormatException extends StorageException {

	public StorageFormatException(String message) {
		super(message);
	}

	public StorageFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
