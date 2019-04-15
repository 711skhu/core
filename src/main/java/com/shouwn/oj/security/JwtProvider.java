package com.shouwn.oj.security;

import java.util.Date;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	@Value("${jwt.secretKey}")
	public String secretKey;

	@Value("${jwt.expirationMs}")
	public long expirationMs;

	private Date now = new Date();

	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	public String generateJwt(Authentication authentication) {
		MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject(memberPrincipal.getUsername())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + expirationMs))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public String getUsernameFromJwt(String jwt) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody()
				.getSubject();
	}

	public boolean validateJwt(String jwt) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
			return true;
		} catch (SecurityException e) {
			logger.error("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token", e);
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token ", e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty", e);
		}

		return false;
	}

}
