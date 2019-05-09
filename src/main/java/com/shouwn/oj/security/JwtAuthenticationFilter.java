package com.shouwn.oj.security;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shouwn.oj.model.response.ApiResponse;
import com.shouwn.oj.model.response.CommonResponse;
import com.shouwn.oj.util.servlet.ResponseUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	private final ObjectMapper objectMapper;

	public JwtAuthenticationFilter(JwtProvider jwtProvider, ObjectMapper objectMapper) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

	/**
	 * 헤더에 jwt 가 있는지 확인하고
	 * 있다면 jwt 에서 memberId를 가져오는 메소드
	 */
	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,
									@Nonnull HttpServletResponse response,
									@Nonnull FilterChain filterChain) throws ServletException, IOException {
		Long memberId = 0L;

		String jwt = jwtFromRequest(request);

		if (StringUtils.isNotBlank(jwt)) {

			try {
				memberId = jwtProvider.getMemberIdFromJwt(jwt);
			} catch (ExpiredJwtException e) {
				String json = objectMapper.writeValueAsString(responseExpiredJwt());
				ResponseUtils.setJsonToResponse(response, json);
				return;
			} catch (JwtException e) {
				String json = objectMapper.writeValueAsString(responseJwtNotValid());
				ResponseUtils.setJsonToResponse(response, json);
				return;
			}

			SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(memberId));
		}

		request.setAttribute("requesterId", memberId);
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

	private ApiResponse<?> responseExpiredJwt() {
		return CommonResponse.builder()
				.status(HttpStatus.NOT_ACCEPTABLE)
				.message("토큰 기간이 만료되었습니다.")
				.build();
	}

	private ApiResponse<?> responseJwtNotValid() {
		return CommonResponse.builder()
				.status(HttpStatus.UNAUTHORIZED)
				.message("토큰이 유효하지 않습니다.")
				.build();
	}
}
