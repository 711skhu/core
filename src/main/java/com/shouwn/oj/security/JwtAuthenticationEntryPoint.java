package com.shouwn.oj.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shouwn.oj.model.response.CommonResponse;
import com.shouwn.oj.util.servlet.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Security 인증 시 예외가 발생했을 때의 익셉션 핸들러
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper;

	public JwtAuthenticationEntryPoint(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		String responseJson = mapper.writeValueAsString(CommonResponse.builder()
				.status(HttpStatus.UNAUTHORIZED)
				.message("인증되지 않은 사용자입니다.")
				.build());

		ResponseUtils.setJsonToResponse(response, responseJson);
	}
}
