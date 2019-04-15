package com.shouwn.oj.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtProviderTest {

	private JwtProvider jwtProvider;

	@Before
	public void setUp() {
		jwtProvider = new JwtProvider();
	}

	/*@Test
	public void test_validateJwt_failure() {
		assertThat(jwtProvider.validateJwt("failure test token")).isFalse();
	}
	*/
}
