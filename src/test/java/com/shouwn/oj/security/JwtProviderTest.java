package com.shouwn.oj.security;

import java.util.Arrays;

import com.shouwn.oj.config.security.JwtTestProperties;
import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

	private JwtTestProperties jwtTestProperties;

	private JwtProvider jwtProvider;

	@BeforeEach
	void init() {
		this.jwtTestProperties = new JwtTestProperties();
		this.jwtProvider = new JwtProvider(jwtTestProperties);
	}

	@Test
	void arrayLengthTest() {
		String[] strings = Arrays.stream("".split(","))
				.filter(StringUtils::isNotBlank)
				.toArray(String[]::new);

		Assertions.assertEquals(0, strings.length);
	}

	@Test
	void getContextFromJwtToSuccess() {
		Long id = 10L;
		String token = jwtProvider.generateJwt(id);

		JwtContext context = jwtProvider.getContextFromJwt(token);

		Assertions.assertEquals(context.getMemberId(), id);
		Assertions.assertEquals(0, context.getRoles().length);

		String role = "ADMIN";
		token = jwtProvider.generateJwt(id, role);

		context = jwtProvider.getContextFromJwt(token);

		Assertions.assertEquals(context.getMemberId(), id);
		Assertions.assertEquals(context.getRoles()[0], role);
	}

	@Test
	void generateJwtByContext() {
		Long id = 10L;
		String role = "TEST";

		JwtContext context = new JwtContext(false, id, role);

		String token = jwtProvider.generateJwt(context);

		JwtContext providedContext = jwtProvider.getContextFromJwt(token);

		Assertions.assertEquals(context, providedContext);
	}

	@Test
	void getContextFromJwtThrowsInvalidParameter() {
		Assertions.assertThrows(InvalidParameterException.class, () ->
				jwtProvider.getContextFromJwt("token")
		);
	}

	@Test
	void getContextFromJwtThrowsIllegalStateException() {
		jwtTestProperties.setExpirationMs(2);

		String token = jwtProvider.generateJwt(10L);

		Assertions.assertThrows(IllegalStateException.class, () ->
				jwtProvider.getContextFromJwt(token)
		);
	}

	@Test
	void refreshTokenTest() {
		Long memberId = 10L;
		String role = "TEST";

		String token = jwtProvider.generateRefreshJwt(memberId, role);

		JwtContext context = jwtProvider.getContextFromJwt(token);

		Assertions.assertTrue(context.isRefreshToken());

		token = jwtProvider.generateJwt(memberId, role);

		context = jwtProvider.getContextFromJwt(token);

		Assertions.assertFalse(context.isRefreshToken());
	}
}
