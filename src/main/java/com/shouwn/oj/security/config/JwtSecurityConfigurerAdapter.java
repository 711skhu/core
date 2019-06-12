package com.shouwn.oj.security.config;

import com.shouwn.oj.security.JwtAuthenticationFilter;
import com.shouwn.oj.security.JwtAuthenticationProvider;
import com.shouwn.oj.security.JwtProperties;
import com.shouwn.oj.security.JwtProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * JwtSecurityConfig 설정을 동작하게 만들기 위해서
 * 상속해서 사용할 설정 어댑터 클래스
 */
public abstract class JwtSecurityConfigurerAdapter {

	@Bean
	public abstract JwtProperties jwtProperties();

	@Bean
	public JwtProvider jwtProvider(JwtProperties jwtProperties) {
		return new JwtProvider(jwtProperties);
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(JwtProvider jwtProvider) {
		return new JwtAuthenticationProvider(jwtProvider);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
