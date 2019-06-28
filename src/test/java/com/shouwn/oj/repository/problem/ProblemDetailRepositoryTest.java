package com.shouwn.oj.repository.problem;

import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class ProblemDetailRepositoryTest {

	@Autowired
	private ProblemDetailRepository problemDetailRepository;

	@Autowired
	private EntityManager em;

	private ProblemDetail sampleProblemDetail;

	@BeforeEach
	public void init() {
		Admin savedProfessor = Admin.builder()
				.name("tester_professor")
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

		sampleProblemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("junit-test")
				.sequence(1)
				.problem(savedProblem)
				.build();

		em.persist(savedProfessor);
		em.persist(savedCourse);
		em.persist(savedProblem);
	}

	private void assertEquals(ProblemDetail expected, ProblemDetail actual) {
		Assertions.assertEquals(expected.getTitle(), actual.getTitle());
		Assertions.assertEquals(expected.getContent(), actual.getContent());
		Assertions.assertEquals(expected.getSequence(), actual.getSequence());
		Assertions.assertEquals(expected.getProblem().getId(), actual.getProblem().getId());
	}

	@Test
	public void saveAndFind() {
		ProblemDetail savedProblemDetail = problemDetailRepository.save(sampleProblemDetail);

		em.flush();
		em.clear();

		ProblemDetail findProblemDetail = problemDetailRepository.findById(savedProblemDetail.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedProblemDetail, findProblemDetail);
	}

	@Test
	public void update() {
		ProblemDetail savedProblemDetail = problemDetailRepository.save(sampleProblemDetail);

		em.flush();

		savedProblemDetail.setTitle("update_junit_test");
		savedProblemDetail.setContent("update_junit_test");
		savedProblemDetail.setSequence(2);

		em.flush();
		em.clear();

		ProblemDetail findProblemDetail = problemDetailRepository.findById(savedProblemDetail.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedProblemDetail, findProblemDetail);
	}

	@Test
	public void delete() {
		ProblemDetail savedProblemDetail = problemDetailRepository.save(sampleProblemDetail);

		em.flush();

		problemDetailRepository.delete(savedProblemDetail);

		em.flush();
		em.clear();

		Assertions.assertFalse(problemDetailRepository.findById(savedProblemDetail.getId()).isPresent());
	}
}
