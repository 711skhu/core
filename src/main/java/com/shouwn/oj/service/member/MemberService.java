package com.shouwn.oj.service.member;

import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.repository.member.MemberRepository;

public abstract class MemberService<T extends Member> {

	private final MemberRepository<T, Long> memberRepository;

	protected MemberService(MemberRepository<T, Long> memberRepository) {
		this.memberRepository = memberRepository;
	}

	public boolean isRegisteredUsername(String username) {
		return memberRepository.findByUsername(username).isPresent();
	}

	public boolean isRegisteredEmail(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}

	public Optional<T> findById(Long id) {
		return memberRepository.findById(id);
	}

	public Optional<T> findByUsername(String username) {
		return memberRepository.findByUsername(username);
	}

	public Optional<T> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	public T save(T member) {
		return memberRepository.save(member);
	}
}
