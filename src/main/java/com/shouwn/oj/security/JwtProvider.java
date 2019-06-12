package com.shouwn.oj.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.exception.InvalidParameterException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class JwtProvider {

	private final JwtProperties jwtProperties;
	private final Key secretKey;
	private final String ROLE_CLAIM_KEY = "role";

	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

		log.info("Token expiration: " + jwtProperties.getExpirationMs() / 1000 + "s. " +
				"Refresh token expiration: " + jwtProperties.getRefreshExpirationMs() / 1000 + "s.");
	}

	private String generateJwt(boolean isRefreshToken, Long id, long expirationMs, String... roles) {
		Date now = new Date();

		return Jwts.builder()
				.setSubject(isRefreshToken ? TokenType.REFRESH.name() : TokenType.AUTH.name())
				.setAudience(String.valueOf(id))
				.claim(ROLE_CLAIM_KEY, String.join(",", roles))
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + expirationMs))
				.signWith(secretKey)
				.compact();
	}

	public String generateJwt(Long id, String... roles) {
		return this.generateJwt(false, id, jwtProperties.getExpirationMs(), roles);
	}

	public String generateJwt(JwtContext context) {
		return this.generateJwt(false, context.getMemberId(), jwtProperties.getExpirationMs(), context.getRoles());
	}

	public String generateRefreshJwt(Long id, String... roles) {
		return this.generateJwt(true, id, jwtProperties.getRefreshExpirationMs(), roles);
	}

	public JwtContext getContextFromJwt(String token) throws IllegalStateException, InvalidParameterException {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody();

			boolean isRefreshToken = TokenType.valueOf(claims.getSubject()) == TokenType.REFRESH;
			Long memberId = Long.valueOf(claims.getAudience());
			String[] roles = Arrays.stream(claims.get(ROLE_CLAIM_KEY, String.class).split(","))
					.filter(StringUtils::isNotBlank)
					.toArray(String[]::new);

			return new JwtContext(isRefreshToken, memberId, roles);
		} catch (ExpiredJwtException ignored) {
			throw new IllegalStateException("EXPIRED");
		} catch (JwtException e) {
			throw new InvalidParameterException("INVALID");
		}
	}

	private enum TokenType {
		AUTH,
		REFRESH
	}

}
