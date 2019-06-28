package com.shouwn.oj.repository.problem;

import java.util.List;
import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EntityManager em;

	private Course sampleCourse;

	@BeforeEach
	public void init() {
		Admin savedProfessor = Admin.builder()
				.name("tester_admin")
				.username("junit_tester_admin")
				.password("test123")
				.email("test@gmail.com")
				.build();

		em.persist(savedProfessor);
		em.flush();

		sampleCourse = Course.builder()
				.name("junit_test_course")
				.description("test")
				.professor(savedProfessor)
				.build();
	}

	private void assertEquals(Course expected, Course actual) {
		Assertions.assertEquals(expected.getName(), actual.getName());
		Assertions.assertEquals(expected.getDescription(), actual.getDescription());
		Assertions.assertEquals(expected.getEnabled(), actual.getEnabled());
		Assertions.assertEquals(expected.getProfessor().getId(), actual.getProfessor().getId());
	}

	@Test
	public void saveAndFind() {
		Course savedCourse = courseRepository.save(sampleCourse);

		em.flush();
		em.clear();

		Course findCourse = courseRepository.findById(savedCourse.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedCourse, findCourse);
	}

	@Test
	public void update() {
		Course savedCourse = courseRepository.save(sampleCourse);

		em.flush();

		savedCourse.setName("update_junit_test");
		savedCourse.setDescription("update_test");
		savedCourse.setEnabled(true);

		em.flush();
		em.clear();

		Course findCourse = courseRepository.findById(savedCourse.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedCourse, findCourse);
	}

	@Test
	public void delete() {
		Course savedCourse = courseRepository.save(sampleCourse);

		courseRepository.delete(savedCourse);

		Assertions.assertFalse(courseRepository.findById(savedCourse.getId()).isPresent());
	}

	@Test
	public void findCoursesByEnable() {
		List<Course> enabledCourses = courseRepository.findCoursesByEnabled(true);
		int beforeCoursesSize = enabledCourses.size();

		sampleCourse.setEnabled(true);
		Course savedCourse = courseRepository.save(sampleCourse);

		enabledCourses = courseRepository.findCoursesByEnabled(true);

		Assertions.assertEquals(beforeCoursesSize + 1, enabledCourses.size());
	}
}
