package com.shouwn.oj.service.member;

import javax.persistence.EntityNotFoundException;

import com.shouwn.oj.exception.member.EmailExistException;
import com.shouwn.oj.exception.member.MemberException;
import com.shouwn.oj.exception.member.UsernameExistException;
import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.repository.member.MemberRepository;
import com.shouwn.oj.security.MemberPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class MemberService implements UserDetailsService {

	private final MemberRepository<? extends Member, Long> memberRepository;

	protected MemberService(MemberRepository<? extends Member, Long> memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username"));

		return MemberPrincipal.of(member);
	}

	public UserDetails loadUserById(Long id) throws EntityNotFoundException {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username"));

		return MemberPrincipal.of(member);
	}

	protected void checkPossibleToMakeMember(Member member) {
		if (member == null) {
			throw new IllegalArgumentException("매개변수로 들어온 admin 이 null 입니다.");
		}

		if (isRegisteredUsername(member.getUsername())) {
			throw new UsernameExistException(member.getUsername() + " 은 이미 등록된 아이디입니다.");
		}

		if (isRegisteredEmail(member.getEmail())) {
			throw new EmailExistException(member.getEmail() + " 은 이미 등록된 이메일입니다.");
		}
	}

	public boolean isRegisteredUsername(String username) {
		return memberRepository.findByUsername(username).isPresent();
	}

	public boolean isRegisteredEmail(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}
}
