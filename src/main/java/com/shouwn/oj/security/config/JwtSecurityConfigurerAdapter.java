package com.shouwn.oj.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.security.*;
import com.shouwn.oj.service.member.MemberAuthService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * JwtSecurityConfig 설정을 동작하게 만들기 위해서
 * 상속해서 사용할 설정 어댑터 클래스
 */
public abstract class JwtSecurityConfigurerAdapter {

	@Bean(name = "securityMemberAuthService")
	public abstract MemberAuthService<? extends Member> memberServiceUsingSecurity();

	@Bean
	public abstract JwtProperties jwtProperties();

	@Bean
	public JwtProvider jwtProvider(JwtProperties jwtProperties) {
		return new JwtProvider(jwtProperties);
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(
			@Qualifier("securityMemberAuthService") MemberAuthService<? extends Member> securityMemberAuthService) {
		return new JwtAuthenticationProvider(securityMemberAuthService);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider, ObjectMapper objectMapper) {
		return new JwtAuthenticationFilter(jwtProvider, objectMapper);
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
		return new JwtAuthenticationEntryPoint(objectMapper);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
