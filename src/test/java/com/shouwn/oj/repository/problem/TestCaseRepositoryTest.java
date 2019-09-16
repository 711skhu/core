package com.shouwn.oj.repository.problem;

import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class TestCaseRepositoryTest {

	@Autowired
	private TestCaseRepository testCaseRepository;

	@Autowired
	private EntityManager em;

	private TestCase sampleTestCase;

	@BeforeEach
	public void init() {
		Admin savedProfessor = Admin.builder()
				.name("tester_admin")
				.username("junit_tester_admin")
				.password("test123")
				.email("test.admin@gmail.com")
				.build();

		Course savedCourse = Course.builder()
				.name("junit_test")
				.description("test")
				.professor(savedProfessor)
				.build();

		savedCourse.setEnabled(true);
		savedProfessor.getCourses().add(savedCourse);

		Problem savedProblem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test_problem")
				.course(savedCourse)
				.build();

		savedCourse.getProblems().add(savedProblem);

		ProblemDetail savedProblemDetail = ProblemDetail.builder()
				.title("junit_test_detail")
				.content("junit-test_detail")
				.sequence(1)
				.problem(savedProblem)
				.build();

		savedProblem.getProblemDetails().add(savedProblemDetail);

		em.persist(savedProfessor);
		em.persist(savedCourse);
		em.persist(savedProblem);
		em.persist(savedProblemDetail);

		sampleTestCase = TestCase.builder()
				.params("1")
				.result("100")
				.problemDetail(savedProblemDetail)
				.build();

		savedProblemDetail.getTestCases().add(sampleTestCase);
	}

	private void assertEquals(TestCase expected, TestCase actual) {
		Assertions.assertEquals(expected.getParams(), actual.getParams());
		Assertions.assertEquals(expected.getResult(), actual.getResult());
		Assertions.assertEquals(expected.getProblemDetail().getId(), actual.getProblemDetail().getId());
	}

	@Test
	public void saveAndFind() {
		TestCase savedTestCase = testCaseRepository.save(sampleTestCase);

		em.flush();
		em.clear();

		TestCase findTestCase = testCaseRepository.findById(savedTestCase.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedTestCase, findTestCase);
	}

	@Test
	public void update() {
		TestCase savedTestCase = testCaseRepository.save(sampleTestCase);

		em.flush();

		savedTestCase.setParams("2");
		savedTestCase.setResult("150");

		em.flush();
		em.clear();

		TestCase findTestCase = testCaseRepository.findById(savedTestCase.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedTestCase, findTestCase);
	}

	@Test
	public void delete() {
		TestCase savedTestCase = testCaseRepository.save(sampleTestCase);

		em.flush();

		testCaseRepository.delete(savedTestCase);

		Assertions.assertFalse(testCaseRepository.findById(savedTestCase.getId()).isPresent());
	}
}
