package com.shouwn.oj.security;

import java.security.Key;
import java.util.Date;

import com.shouwn.oj.config.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	private final JwtProperties jwtProperties;

	private final Key secretKey;

	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
	}

	public String generateJwt(Long id) {
		Date now = new Date();

		return Jwts.builder()
				.setSubject(String.valueOf(id))
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + jwtProperties.getExpirationMs()))
				.signWith(secretKey)
				.compact();
	}

	public Long getMemberIdFromJwt(String token) throws JwtException, IllegalArgumentException {
		Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());
	}

}
