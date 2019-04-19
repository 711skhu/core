package com.shouwn.oj.security;

import java.util.Collection;
import java.util.Collections;

import com.shouwn.oj.model.entity.member.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberPrincipal implements UserDetails {

	private String username;

	private String password;

	private String role;

	private MemberPrincipal(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public static MemberPrincipal of(Member member) {
		return new MemberPrincipal(member.getUsername(), member.getPassword(), member.getRole());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
