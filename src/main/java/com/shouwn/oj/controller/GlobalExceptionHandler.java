package com.shouwn.oj.controller;

import java.lang.annotation.Annotation;
import java.util.Map;

import com.shouwn.oj.exception.OJException;
import com.shouwn.oj.model.response.ApiResponse;
import com.shouwn.oj.model.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OJException.class)
	public ApiResponse ojExceptionHandler(OJException e) {
		log.info(e.getMessage(), e);

		Annotation annotation = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

		Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(annotation);

		return CommonResponse.builder()
				.status(HttpStatus.valueOf((int) attributes.get("code")))
				.message(e.getMessage())
				.build();
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ApiResponse AccessDeniedExceptionHandler(AccessDeniedException e) {
		return CommonResponse.builder()
				.status(HttpStatus.UNAUTHORIZED)
				.message(e.getMessage())
				.build();
	}

	@ExceptionHandler(Exception.class)
	public ApiResponse otherExceptionHandler(Exception e) {
		log.error(e.getMessage(), e);

		return CommonResponse.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.message("내부 오류")
				.build();
	}
}