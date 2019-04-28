package com.shouwn.oj.service.member;

import com.shouwn.oj.exception.member.EmailNotExistException;
import com.shouwn.oj.exception.member.IdNotExistException;
import com.shouwn.oj.exception.member.UsernameNotExistException;
import com.shouwn.oj.model.entity.member.Member;

public interface MemberService<T extends Member> {

	T findById(Long id) throws IdNotExistException;

	T findByUsername(String username) throws UsernameNotExistException;

	T findByEmail(String email) throws EmailNotExistException;

	default boolean isRegisteredUsername(String username) {
		try {
			findByUsername(username);
			return true;
		} catch (UsernameNotExistException e) {
			return false;
		}
	}

	default boolean isRegisteredEmail(String email) {
		try {
			findByEmail(email);
			return true;
		} catch (EmailNotExistException e) {
			return false;
		}
	}
}
