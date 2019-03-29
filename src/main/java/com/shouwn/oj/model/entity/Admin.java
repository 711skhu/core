package com.shouwn.oj.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Member {

	@Builder
	public Admin(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
