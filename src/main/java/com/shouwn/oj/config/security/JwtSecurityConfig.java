package com.shouwn.oj.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shouwn.oj.security.JwtAuthenticationEntryPoint;
import com.shouwn.oj.security.JwtAuthenticationFilter;
import com.shouwn.oj.security.JwtProvider;
import com.shouwn.oj.service.member.MemberService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtProvider jwtProvider;

	private final ObjectMapper objectMapper;

	private final MemberService securityMemberService;

	public JwtSecurityConfig(JwtProvider jwtProvider,
							 ObjectMapper objectMapper,
							 @Qualifier("securityMemberService") MemberService securityMemberService) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.securityMemberService = securityMemberService;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtProvider, objectMapper, securityMemberService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(securityMemberService);
		super.configure(authenticationManagerBuilder);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint());

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint(objectMapper);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
