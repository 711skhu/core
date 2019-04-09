package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.repository.member.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Test
	public void saveAndFind() {
		Admin professor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(professor);

		Course newCourse = Course.builder()
				.name("junit_test")
				.description("test")
				.enabled(false)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Course findCourse = courseRepository.findById(newCourse.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(newCourse.getName(), findCourse.getName());
		Assertions.assertEquals(newCourse.getDescription(), findCourse.getDescription());
		Assertions.assertEquals(newCourse.getEnabled(), findCourse.getEnabled());
		Assertions.assertEquals(newCourse.getProfessor(), findCourse.getProfessor());
	}

	@Test
	public void update() {
		Admin professor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(professor);

		Course newCourse = Course.builder()
				.name("junit_test")
				.description("test")
				.enabled(false)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		newCourse.setName("update_junit_test");
		newCourse.setDescription("update_test");
		newCourse.setEnabled(true);

		Course findCourse = courseRepository.findById(newCourse.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(newCourse.getName(), findCourse.getName());
		Assertions.assertEquals(newCourse.getDescription(), findCourse.getDescription());
		Assertions.assertEquals(newCourse.getEnabled(), findCourse.getEnabled());
		Assertions.assertEquals(newCourse.getProfessor(), findCourse.getProfessor());

	}

	@Test
	public void delete() {
		Admin professor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(professor);

		Course newCourse = Course.builder()
				.name("junit_test")
				.description("test")
				.enabled(false)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Course findCourse = courseRepository.findById(newCourse.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(newCourse.getName(), findCourse.getName());
		Assertions.assertEquals(newCourse.getDescription(), findCourse.getDescription());
		Assertions.assertEquals(newCourse.getEnabled(), findCourse.getEnabled());
		Assertions.assertEquals(newCourse.getProfessor(), findCourse.getProfessor());

		courseRepository.delete(newCourse);

		Assertions.assertFalse(courseRepository.findById(newCourse.getId()).isPresent());
	}

}
