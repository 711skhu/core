package com.shouwn.oj.security;

import com.shouwn.oj.config.jwt.JwtConfig;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JwtConfig.class)
@Import(value = JwtTestConfig.class)
public class JwtProviderTest {

	private JwtProvider jwtProvider;

	@BeforeEach
	public void setUp() {
		jwtProvider = new JwtProvider(new JwtTestProperties());
	}

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
