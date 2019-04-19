package com.shouwn.oj.security;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shouwn.oj.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	private final MemberService memberService;

	public JwtAuthenticationFilter(JwtProvider jwtProvider, MemberService memberService) {
		this.jwtProvider = jwtProvider;
		this.memberService = memberService;
	}

	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,
									@Nonnull HttpServletResponse response,
									@Nonnull FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = jwtFromRequest(request);

			if (StringUtils.isNotBlank(jwt)) {
				UserDetails userDetails = memberService.loadUserById(jwtProvider.getMemberIdFromJwt(jwt));
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Could not set user authentication in security context", e);
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
