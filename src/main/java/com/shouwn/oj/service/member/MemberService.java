package com.shouwn.oj.service.member;

import javax.persistence.EntityNotFoundException;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.repository.member.MemberRepository;
import com.shouwn.oj.security.MemberPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

}
