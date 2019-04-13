package com.shouwn.oj.model.entity.member;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.shouwn.oj.model.entity.problem.Course;
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

	@Override
	public String getRole() {
		return "STUDENT";
	}

	@Builder
	public Student(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
