package com.shouwn.oj.security;

import lombok.Data;

@Data
public class JwtContext {

	private final boolean isRefreshToken;

	private final Long memberId;

	private final String[] roles;

	JwtContext(boolean isRefreshToken, Long memberId, String... roles) {
		this.isRefreshToken = isRefreshToken;
		this.memberId = memberId;
		this.roles = roles;
	}
}
