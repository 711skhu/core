package com.shouwn.oj.repository.problem;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.repository.member.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@ExtendWith(SpringExtension.class)
@Import(RepositoryTestConfig.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProblemDetailRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProblemDetailRepository problemDetailRepository;

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

		ProblemDetail problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("junit-test")
				.sequence(1)
				.problem(problem)
				.build();

		problemDetailRepository.save(problemDetail);

		ProblemDetail findProblemDetail = problemDetailRepository.findById(problemDetail.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problemDetail.getTitle(), findProblemDetail.getTitle());
		Assertions.assertEquals(problemDetail.getContent(), findProblemDetail.getContent());
		Assertions.assertEquals(problemDetail.getSequence(), findProblemDetail.getSequence());
		Assertions.assertEquals(problemDetail.getProblem(), findProblemDetail.getProblem());
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

		ProblemDetail problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("junit-test")
				.sequence(1)
				.problem(problem)
				.build();

		problemDetailRepository.save(problemDetail);

		problemDetail.setTitle("update_junit_test");
		problemDetail.setContent("udpate_junit_test");
		problemDetail.setSequence(2);

		ProblemDetail findProblemDetail = problemDetailRepository.findById(problemDetail.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problemDetail.getTitle(), findProblemDetail.getTitle());
		Assertions.assertEquals(problemDetail.getContent(), findProblemDetail.getContent());
		Assertions.assertEquals(problemDetail.getSequence(), findProblemDetail.getSequence());
		Assertions.assertEquals(problemDetail.getProblem(), findProblemDetail.getProblem());
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

		ProblemDetail problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("junit-test")
				.sequence(1)
				.problem(problem)
				.build();

		problemDetailRepository.save(problemDetail);

		ProblemDetail findProblemDetail = problemDetailRepository.findById(problemDetail.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problemDetail.getTitle(), findProblemDetail.getTitle());
		Assertions.assertEquals(problemDetail.getContent(), findProblemDetail.getContent());
		Assertions.assertEquals(problemDetail.getSequence(), findProblemDetail.getSequence());
		Assertions.assertEquals(problemDetail.getProblem(), findProblemDetail.getProblem());

		problemDetailRepository.delete(problemDetail);

		Assertions.assertFalse(problemDetailRepository.findById(problemDetail.getId()).isPresent());
	}
}
