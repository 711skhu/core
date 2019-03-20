package com.shouwn.oj.model.entity;

import javax.persistence.*;

import com.shouwn.oj.model.enums.Role;
import com.shouwn.oj.util.KotlinStyle;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "members")
public class Member extends BaseEntity implements KotlinStyle<Member> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, updatable = false, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public Member(String username, String password, String nickname, String email, Role role) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.role = role;
	}
}