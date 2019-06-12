package com.shouwn.oj.service.member;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.repository.member.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class MemberAuthService<T extends Member> {

	private final MemberRepository<T, Long> memberRepository;

	private final PasswordEncoder passwordEncoder;

	protected MemberAuthService(MemberRepository<T, Long> memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public boolean isPasswordStrengthWeak(String rawPassword) {
		if (rawPassword.length() < 8) {
			return true;
		}

		if (!rawPassword.matches(".*[A-Za-z]+.*")) {
			return true;
		}

		if (!rawPassword.matches(".*\\d+.*")) {
			return true;
		}

		return false;
	}

	public String passwordEncode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public boolean isCorrectPassword(Member member, String rawPassword) {
		return passwordEncoder.matches(rawPassword, member.getPassword());
	}
}
