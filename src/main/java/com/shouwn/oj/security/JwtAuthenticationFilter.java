package com.shouwn.oj.security;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * 헤더에 jwt 가 있는지 확인하고
	 * 있다면 JwtAuthenticationToken 을 등록하는 필터
	 */
	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,
									@Nonnull HttpServletResponse response,
									@Nonnull FilterChain filterChain) throws ServletException, IOException {

		String jwt = jwtFromRequest(request);

		if (StringUtils.isNotBlank(jwt)) {
			SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
		}

		filterChain.doFilter(request, response);
	}

	private String jwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		} else {
			return null;
		}
	}
}
