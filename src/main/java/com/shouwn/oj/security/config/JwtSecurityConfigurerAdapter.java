package com.shouwn.oj.security.config;

import com.shouwn.oj.security.JwtProperties;
import com.shouwn.oj.security.JwtProvider;
import com.shouwn.oj.service.member.MemberService;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;

public abstract class JwtSecurityConfigurerAdapter {

	@Bean(name = "securityMemberService")
	public abstract MemberService memberServiceUsingSecurity();

	@Bean
	public JwtProvider jwtProvider(JwtProperties jwtProperties) {
		return new JwtProvider(jwtProperties);
	}

	@Bean
	public abstract JwtProperties jwtProperties();
}
