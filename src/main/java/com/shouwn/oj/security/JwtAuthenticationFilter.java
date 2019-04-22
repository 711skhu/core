package com.shouwn.oj.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shouwn.oj.model.response.ApiResponse;
import com.shouwn.oj.model.response.CommonResponse;
import com.shouwn.oj.service.member.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	private final ObjectMapper objectMapper;

	private final MemberService memberService;

	public JwtAuthenticationFilter(JwtProvider jwtProvider, ObjectMapper objectMapper, MemberService memberService) {
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.memberService = memberService;
	}

	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,
									@Nonnull HttpServletResponse response,
									@Nonnull FilterChain filterChain) throws ServletException, IOException {
		Long memberId = null;

		String jwt = jwtFromRequest(request);

		if (StringUtils.isNotBlank(jwt)) {

			try {
				memberId = jwtProvider.getMemberIdFromJwt(jwt);
			} catch (ExpiredJwtException e) {
				setApiResponseToServletResponse(response, responseExpiredJwt());
				return;
			} catch (JwtException e) {
				setApiResponseToServletResponse(response, responseJwtNotValid());
				return;
			}

			Authentication authentication = authenticationFromMemberId(memberId, request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
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

	private Authentication authenticationFromMemberId(Long memberId, HttpServletRequest request) {
		UserDetails userDetails = memberService.loadUserById(memberId);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return authentication;
	}

	private void setApiResponseToServletResponse(HttpServletResponse response, ApiResponse<?> apiResponse) throws IOException {
		String json = objectMapper.writeValueAsString(apiResponse);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
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
