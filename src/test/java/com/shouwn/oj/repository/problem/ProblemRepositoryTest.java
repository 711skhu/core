package com.shouwn.oj.repository.problem;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.repository.member.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.shouwn.oj.model.enums.ProblemType.EXAM;
import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@ExtendWith(SpringExtension.class)
@Import(RepositoryTestConfig.class)
@DataJpaTest
public class ProblemRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ProblemRepository problemRepository;

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
				.enabled(true)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Problem problem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test")
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		Problem findProblem = problemRepository.findById(problem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problem.getType(), findProblem.getType());
		Assertions.assertEquals(problem.getTitle(), findProblem.getTitle());
		Assertions.assertEquals(problem.getStartDate(), findProblem.getStartDate());
		Assertions.assertEquals(problem.getEndDate(), findProblem.getEndDate());
		Assertions.assertEquals(problem.getCourse(), findProblem.getCourse());
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
				.enabled(true)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Problem problem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test")
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		problem.setType(EXAM);
		problem.setTitle("update_junit_test");

		Problem findProblem = problemRepository.findById(problem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problem.getType(), findProblem.getType());
		Assertions.assertEquals(problem.getTitle(), findProblem.getTitle());
		Assertions.assertEquals(problem.getStartDate(), findProblem.getStartDate());
		Assertions.assertEquals(problem.getEndDate(), findProblem.getEndDate());
		Assertions.assertEquals(problem.getCourse(), findProblem.getCourse());
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
				.enabled(true)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Problem problem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test")
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		Problem findProblem = problemRepository.findById(problem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problem.getType(), findProblem.getType());
		Assertions.assertEquals(problem.getTitle(), findProblem.getTitle());
		Assertions.assertEquals(problem.getStartDate(), findProblem.getStartDate());
		Assertions.assertEquals(problem.getEndDate(), findProblem.getEndDate());
		Assertions.assertEquals(problem.getCourse(), findProblem.getCourse());

		problemRepository.delete(problem);

		Assertions.assertFalse(problemRepository.findById(problem.getId()).isPresent());
	}
}
