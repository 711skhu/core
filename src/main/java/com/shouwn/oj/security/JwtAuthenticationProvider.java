package com.shouwn.oj.security;

import java.util.Collections;
import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.service.member.MemberAuthService;
import org.apache.commons.lang3.reflect.TypeUtils;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final MemberAuthService<? extends Member> memberAuthService;

	public JwtAuthenticationProvider(MemberAuthService<? extends Member> memberAuthService) {
		this.memberAuthService = memberAuthService;
	}

	/**
	 * JwtAuthenticationToken 으로 받은 memberId 로 조회를 해서
	 * 해당 사용자가 있는지 체크하고 Spring Security 인증을 적용하는 메소드
	 *
	 * @param authentication 인증 객체
	 * @return 인증 객체에서 얻은 memberId 로 조회한 사용자의 인증 정보
	 * @throws AuthenticationException memberId로 사용자를 찾을 수 없을 때 발생
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!TypeUtils.isInstance(authentication, JwtAuthenticationToken.class)) {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " 클래스는 "
					+ JwtAuthenticationToken.class.getCanonicalName() + "만 인식할 수 있습니다.");
		}

		JwtAuthenticationToken auth = (JwtAuthenticationToken) authentication;
		Long memberId = auth.getMemberId();

		Optional<? extends Member> member = memberAuthService.findById(memberId);

		if (!member.isPresent()) {
			throw new UsernameNotFoundException(memberId + " 로 유저를 찾을 수 없습니다.");
		}

		return new UsernamePasswordAuthenticationToken(
				member.get().getUsername(), member.get().getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(member.get().getRole())));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(JwtAuthenticationToken.class);
	}
}
