package com.shouwn.oj.security;

import java.util.Date;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	@Value("${jwt.secretKey}")
	public String secretKey;

	@Value("${jwt.expirationMs}")
	public long expirationMs;

	private Date now = new Date();

	public String generateJwt(Long id) {
		return Jwts.builder()
				.setSubject(String.valueOf(id))
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + expirationMs))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public Long getUserIdFromJwt(String token) {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();
			return Long.parseLong(claims.getId());
	}

}
