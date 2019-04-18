package com.shouwn.oj.config.jwt;

public interface JwtProperties {

	String getSecretKey();

	long getExpirationMs();
}
