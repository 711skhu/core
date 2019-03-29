package com.shouwn.oj.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonResponse<T> implements ApiResponse<T> {

	private int status;

	private String message;

	private T data;
}
