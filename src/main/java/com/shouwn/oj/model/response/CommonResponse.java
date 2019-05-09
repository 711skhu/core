package com.shouwn.oj.model.response;

import lombok.Builder;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T> implements ApiResponse<T> {

	private int code;

	private String status;

	private String message;

	private T data;

	@Builder
	public CommonResponse(HttpStatus status, String message, T data) {
		this.code = status.value();
		this.status = status.getReasonPhrase();
		this.message = message;
		this.data = data;
	}
}
