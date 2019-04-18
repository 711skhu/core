package com.shouwn.oj.config.jwt;

import com.shouwn.oj.security.JwtProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	@Bean
	public JwtProvider jwtProvider(JwtProperties jwtProperties) {
		return new JwtProvider(jwtProperties);
	}

	@ConditionalOnMissingBean
	@Bean
	public JwtProperties jwtProperties() {
		return new DefaultJwtProperties();
	}
}
