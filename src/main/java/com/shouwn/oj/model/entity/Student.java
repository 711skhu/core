package com.shouwn.oj.model.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Member {

	@Builder
	public Student(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
