package com.shouwn.oj.security;

import io.jsonwebtoken.JwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtProviderTest {

	private JwtProvider jwtProvider;

	@Before
	public void setUp() {
		jwtProvider = new JwtProvider(new JwtTestProperties());
	}

	@Test
	public void getMemberIdFromJwtToSuccess() {
		Long id = 10L;
		String token = jwtProvider.generateJwt(id);

		Assertions.assertEquals(jwtProvider.getMemberIdFromJwt(token), id);
	}

	@Test(expected = JwtException.class)
	public void getMemberIdFromJwtThrowsJwtException() {
		jwtProvider.getMemberIdFromJwt("token");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getMemberIdFromJwtThrowsIllegalArgumentException() {
		jwtProvider.getMemberIdFromJwt(null);
	}

}
