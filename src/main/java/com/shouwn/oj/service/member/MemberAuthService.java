package com.shouwn.oj.service.member;

import com.shouwn.oj.exception.member.EmailExistException;
import com.shouwn.oj.exception.member.PasswordStrengthLeakException;
import com.shouwn.oj.exception.member.UsernameExistException;
import com.shouwn.oj.model.entity.member.Member;

public interface MemberAuthService<T extends Member> extends MemberService<T> {

	/**
	 * 회원을 만들 수 있는지 체크하는 메소드
	 * 아이디, 비밀번호 강도, 이메일을 체크
	 *
	 * @param member 만들 회원 객체
	 * @throws UsernameExistException        아이디가 이미 등록되었을 때 발생하는 예외
	 * @throws PasswordStrengthLeakException 비밀번호 강도가 약할 때 발생하는 예외
	 * @throws EmailExistException           이메일이 이미 등록되었을 때 발생하는 예외
	 */
	default void checkPossibleToMakeMember(T member)
			throws UsernameExistException, PasswordStrengthLeakException, EmailExistException {
		if (member == null) {
			throw new IllegalArgumentException("매개변수로 들어온 admin 이 null 입니다.");
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

	/**
	 * 비밀번호 강도를 확인하는 메소드
	 *
	 * @param rawPassword 인코딩되지 않은 비밀번호
	 * @return 비밀번호 강도를 만족했는지 여부
	 */
	default boolean isPasswordStrengthGood(String rawPassword) {
		return rawPassword.length() > 8;
	}

	boolean isCorrectPassword(Member member, String rawPassword);
}
