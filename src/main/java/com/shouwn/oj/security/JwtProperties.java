package com.shouwn.oj.security;

public interface JwtProperties {

	String getSecretKey();

	long getExpirationMs();
}
