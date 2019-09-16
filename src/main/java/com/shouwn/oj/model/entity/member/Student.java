package com.shouwn.oj.model.entity.member;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.shouwn.oj.model.entity.problem.CourseRegister;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Member {

	@OneToMany(mappedBy = "student")
	private List<CourseRegister> registers = new ArrayList<>();

	@Builder
	public Student(String username, String password, String name, String email) {
		super(username, password, name, email);
	}

	@Override
	public String getRole() {
		return "ROLE_STUDENT";
	}
}
