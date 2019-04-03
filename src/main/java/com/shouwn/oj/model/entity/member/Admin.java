package com.shouwn.oj.model.entity.member;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.shouwn.oj.model.entity.problem.Course;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Member {

	@OneToMany(mappedBy = "professor")
	private List<Course> courses = new ArrayList<>();

	@Builder
	public Admin(String username, String password, String name, String email) {
		super(username, password, name, email);
	}
}
