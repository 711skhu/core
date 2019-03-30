package com.shouwn.oj.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Member {

	@ManyToMany
	@JoinTable(
			name = "member_course",
			joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id")
	)
	private List<Course> courses = new ArrayList<>();

	@Builder
	public Student(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
