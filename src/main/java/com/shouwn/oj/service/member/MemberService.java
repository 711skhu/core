package com.shouwn.oj.service.member;

import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;

public interface MemberService<T extends Member> {

	Optional<T> findById(Long id);

	Optional<T> findByUsername(String username);

	Optional<T> findByEmail(String email);

	default boolean isRegisteredUsername(String username) {
		return findByUsername(username).isPresent();
	}

	default boolean isRegisteredEmail(String email) {
		return findByEmail(email).isPresent();
	}
}
