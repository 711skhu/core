package com.shouwn.oj.model.entity.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;

import com.shouwn.oj.model.entity.problem.Course;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Member {

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	private List<Course> courses = new ArrayList<>();

	@Override
	public String getRole() {
		return "ADMIN";
	}

	@Builder
	public Admin(String username, String password, String name, String email) {
		super(username, password, name, email);
	}

	// make course
	public Course makeCourse(String courseName, String courseDescription) throws EntityExistsException {
		if (courses.stream().anyMatch(c -> StringUtils.equals(c.getName(), courseName))) {
			throw new EntityExistsException(courseName + " 라는 이름의 강의를 이미 만들었습니다.");
		}

		Course course = Course.builder()
				.name(courseName)
				.description(courseDescription)
				.enabled(false)
				.professor(this)
				.build();

		courses.add(course);

		return course;
	}

	// update course
	public Course updateCourse(Long courseId, String courseName, String courseDescription){
		Course course=null;

		for(Course c : courses){
			if(c.getId().equals(courseId)){
				course = c;
				break;
			}
		}

		// update
		course.setName(courseName);
		course.setDescription(courseDescription);

		return course;
	}

	// active course
	public void activeCourse(Long courseId, Boolean enabled){

	}

	// delete course
	public void deleteCourse(Long courseId){

		for(Iterator<Course> it = courses.iterator(); it.hasNext();){
			Course course = it.next();

			if(course.getId().equals(courseId)){
				it.remove();
				break;
			}
		}

	}
}
