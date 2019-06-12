package com.shouwn.oj.service.member;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.repository.member.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberAuthServiceTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AdminRepository adminRepository;

	private MemberAuthService<Admin> authService;

	@BeforeEach
	void init() {
		authService = new AdminAuthService(adminRepository, passwordEncoder);
	}

	@Test
	void isPasswordStrengthWeak() {

		String shortPwd = "t1Ta";

		Assertions.assertTrue(authService.isPasswordStrengthWeak(shortPwd));

		String notContainsNumberPwd = "GOIjgroINowijeGOW";

		Assertions.assertTrue(authService.isPasswordStrengthWeak(notContainsNumberPwd));

		String goodPwd = "good1234";

		Assertions.assertFalse(authService.isPasswordStrengthWeak(goodPwd));
	}

	@Test
	void passwordEncode() {
		String testResult = "encoded";

		when(passwordEncoder.encode(any())).thenReturn(testResult);

		String pwd = "test123";

		Assertions.assertEquals(testResult, authService.passwordEncode(pwd));
	}
}