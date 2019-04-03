package com.shouwn.oj.model.entity.member;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.shouwn.oj.model.entity.BaseEntity;
import com.shouwn.oj.model.entity.problem.Solution;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public abstract class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, updatable = false, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@OneToMany(mappedBy = "member")
	private List<Solution> solutions = new ArrayList<>();

	public Member(String username, String password, String name, String email) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}
}