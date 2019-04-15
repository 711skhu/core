package com.shouwn.oj.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.shouwn.oj.model.entity.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberPrincipal implements UserDetails {

	private Long id;

	private String username;

	private String password;

	private String name;

	private String email;

	private Collection<? extends GrantedAuthority> authorities;

	private MemberPrincipal(Long id, String username, String password, String name,
							String email, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.authorities = authorities;
	}

	public static MemberPrincipal of(Member member) {
		List<? extends GrantedAuthority> authorities =
				Arrays.asList(new SimpleGrantedAuthority(member.getRole()));

		return new MemberPrincipal(
				member.getId(),
				member.getUsername(),
				member.getPassword(),
				member.getName(),
				member.getEmail(),
				authorities
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
