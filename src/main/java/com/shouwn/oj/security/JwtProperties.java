package com.shouwn.oj.security;

public interface JwtProperties {

	String getSecretKey();

	default long getExpirationMs() {
		return 3_600_000L; // 1 hour
	}

	default long getRefreshExpirationMs() {
		return 1_209_600_000L; // 2 week
	}
}
