package com.shouwn.oj.security;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.shouwn.oj.exception.AuthenticationFailedException;
import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.exception.OJException;
import org.apache.commons.lang3.reflect.TypeUtils;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JwtProvider jwtProvider;

	public JwtAuthenticationProvider(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	/**
	 * JWT 를 이용해서 인증하는 메소드
	 *
	 * @param authentication 인증 객체
	 * @return 인증 객체에서 얻은 memberId 로 조회한 사용자의 인증 정보
	 * @throws AuthenticationException memberId로 사용자를 찾을 수 없을 때 발생
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		if (!TypeUtils.isInstance(authentication, JwtAuthenticationToken.class)) {
			throw new IllegalStateException("This provider only support: " +
					JwtAuthenticationToken.class.getCanonicalName());
		}

		JwtAuthenticationToken auth = (JwtAuthenticationToken) authentication;

		try {
			JwtContext context = jwtProvider.getContextFromJwt(auth.getToken());

			if (context.isRefreshToken()) {
				throw new AuthenticationFailedException("리프레시 토큰으로 인증할 수 없습니다.");
			}

			return new UsernamePasswordAuthenticationToken(context.getMemberId(), null,
					Arrays.stream(context.getRoles()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		} catch (OJException e) {
			throw new AuthenticationFailedException(e.getMessage());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(JwtAuthenticationToken.class);
	}
}
