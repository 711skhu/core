package com.shouwn.oj.security;

import java.util.Collection;

import lombok.Getter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * JWT 로 부터 얻은 사용자의 memberId 를 가지고 있는 인증 객체
 */
@Getter
public class JwtAuthenticationToken implements Authentication {

	private final String token;

	JwtAuthenticationToken(String token) {
		this.token = token;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return null;
	}
}
