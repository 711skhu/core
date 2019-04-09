package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.repository.member.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class TestCaseRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProblemDetailRepository problemDetailRepository;

	@Autowired
	private TestCaseRepository testCaseRepository;

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

		TestCase testCase = TestCase.builder()
				.params("1")
				.result("100")
				.problemDetail(problemDetail)
				.build();

		testCaseRepository.save(testCase);

		TestCase findTestCase = testCaseRepository.findById(testCase.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(testCase.getParams(), findTestCase.getParams());
		Assertions.assertEquals(testCase.getResult(), findTestCase.getResult());
		Assertions.assertEquals(testCase.getProblemDetail(), findTestCase.getProblemDetail());
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

		TestCase testCase = TestCase.builder()
				.params("1")
				.result("100")
				.problemDetail(problemDetail)
				.build();

		testCaseRepository.save(testCase);

		testCase.setParams("2");
		testCase.setResult("150");

		TestCase findTestCase = testCaseRepository.findById(testCase.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(testCase.getParams(), findTestCase.getParams());
		Assertions.assertEquals(testCase.getResult(), findTestCase.getResult());
		Assertions.assertEquals(testCase.getProblemDetail(), findTestCase.getProblemDetail());
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

		TestCase testCase = TestCase.builder()
				.params("1")
				.result("100")
				.problemDetail(problemDetail)
				.build();

		testCaseRepository.save(testCase);

		TestCase findTestCase = testCaseRepository.findById(testCase.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(testCase.getParams(), findTestCase.getParams());
		Assertions.assertEquals(testCase.getResult(), findTestCase.getResult());
		Assertions.assertEquals(testCase.getProblemDetail(), findTestCase.getProblemDetail());

		testCaseRepository.delete(testCase);

		Assertions.assertFalse(testCaseRepository.findById(testCase.getId()).isPresent());
	}
}
