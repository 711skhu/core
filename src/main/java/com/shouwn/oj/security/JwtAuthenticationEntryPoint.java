package com.shouwn.oj.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shouwn.oj.model.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper;

	public JwtAuthenticationEntryPoint(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		String responseJson = mapper.writeValueAsString(CommonResponse.builder()
				.status(HttpStatus.UNAUTHORIZED)
				.message("인증되지 않은 사용자입니다.")
				.build());

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();
		out.print(responseJson);
		out.flush();
	}
}
