package com.shouwn.oj.config.security;

import com.shouwn.oj.security.JwtAuthenticationEntryPoint;
import com.shouwn.oj.security.JwtAuthenticationFilter;
import com.shouwn.oj.security.JwtAuthenticationProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 에 Jwt 를 적용하는 설정
 */
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public JwtSecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider,
							 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
							 JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		super.configure(authenticationManagerBuilder);
		authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
