package com.shouwn.oj.security;

import com.shouwn.oj.config.security.JwtTestConfig;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(JwtTestConfig.class)
@SpringBootTest(classes = JwtProvider.class)
public class JwtProviderTest {

	@Autowired
	private JwtProvider jwtProvider;

	@Test
	public void getMemberIdFromJwtToSuccess() {
		Long id = 10L;
		String token = jwtProvider.generateJwt(id);

		Assertions.assertEquals(jwtProvider.getMemberIdFromJwt(token), id);
	}

	@Test
	public void getMemberIdFromJwtThrowsJwtException() {
		Assertions.assertThrows(JwtException.class, () ->
				jwtProvider.getMemberIdFromJwt("token")
		);
	}

	@Test
	public void getMemberIdFromJwtThrowsIllegalArgumentException() {
		Assertions.assertThrows(IllegalArgumentException.class, () ->
				jwtProvider.getMemberIdFromJwt(null)
		);
	}

}
