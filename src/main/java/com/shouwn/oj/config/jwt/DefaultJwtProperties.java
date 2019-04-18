package com.shouwn.oj.config.jwt;

public class DefaultJwtProperties implements JwtProperties {

	@Override
	public String getSecretKey() {
		throw new IllegalStateException("must be register jwtProperties bean");
	}

	@Override
	public long getExpirationMs() {
		throw new IllegalStateException("must be register jwtProperties bean");
	}
}
