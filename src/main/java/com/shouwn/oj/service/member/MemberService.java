package com.shouwn.oj.service.member;

import javax.persistence.EntityNotFoundException;

import com.shouwn.oj.exception.member.*;
import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.security.MemberPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class MemberService implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;

	protected MemberService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Member member = findByUsername(username);
			return MemberPrincipal.of(member);
		} catch (EntityNotFoundException e) {
			throw new UsernameNotFoundException("User not found with username");
		}
	}

	public UserDetails loadUserById(Long id) throws EntityNotFoundException {
		Member member = findById(id);
		return MemberPrincipal.of(member);
	}

	public abstract Member findById(Long id) throws IdNotExistException;

	public abstract Member findByUsername(String username) throws UsernameNotExistException;

	public abstract Member findByEmail(String email) throws EmailNotExistException;

	/**
	 * 회원을 만들 수 있는지 체크하는 메소드
	 * 아이디, 비밀번호 강도, 이메일을 체크
	 *
	 * @param member 만들 회원 객체
	 * @throws UsernameExistException        아이디가 이미 등록되었을 때 발생하는 예외
	 * @throws PasswordStrengthLeakException 비밀번호 강도가 약할 때 발생하는 예외
	 * @throws EmailExistException           이메일이 이미 등록되었을 때 발생하는 예외
	 */
	protected <T extends Member> void checkPossibleToMakeMember(T member)
			throws UsernameExistException, PasswordStrengthLeakException, EmailExistException {
		if (member == null) {
			throw new IllegalArgumentException("매개변수로 들어온 member 가 null 입니다.");
		}

		if (isRegisteredUsername(member.getUsername())) {
			throw new UsernameExistException(member.getUsername() + " 은 이미 등록된 아이디입니다.");
		}

		if (isPasswordStrengthGood(member.getPassword())) {
			throw new PasswordStrengthLeakException("비밀번호 강도가 약합니다.");
		}

		if (isRegisteredEmail(member.getEmail())) {
			throw new EmailExistException(member.getEmail() + " 은 이미 등록된 이메일입니다.");
		}
	}

	public boolean isRegisteredUsername(String username) {
		try {
			findByUsername(username);
			return true;
		} catch (UsernameNotExistException e) {
			return false;
		}
	}

	public boolean isRegisteredEmail(String email) {
		try {
			findByEmail(email);
			return true;
		} catch (EmailNotExistException e) {
			return false;
		}
	}

	public boolean isPasswordStrengthGood(String password) {
		return password.length() > 8;
	}

	public boolean isCorrectPassword(Member member, String rawPassword) {
		return passwordEncoder.matches(rawPassword, member.getPassword());
	}
}
