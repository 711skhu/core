package com.shouwn.oj.repository.problem;

import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.shouwn.oj.model.enums.ProblemType.EXAM;
import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class ProblemRepositoryTest {

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private EntityManager em;

	private Problem sampleProblem;

	@BeforeEach
	public void init() {
		Admin savedProfessor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		Course savedCourse = Course.builder()
				.name("junit_test")
				.description("test")
				.professor(savedProfessor)
				.build();

		savedCourse.setEnabled(true);
		savedProfessor.getCourses().add(savedCourse);

		em.persist(savedProfessor);
		em.persist(savedCourse);

		sampleProblem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test")
				.course(savedCourse)
				.build();

		savedCourse.getProblems().add(sampleProblem);
	}

	private void assertEquals(Problem expected, Problem actual) {
		Assertions.assertEquals(expected.getType(), actual.getType());
		Assertions.assertEquals(expected.getTitle(), actual.getTitle());
		Assertions.assertEquals(expected.getStartDate(), actual.getStartDate());
		Assertions.assertEquals(expected.getEndDate(), actual.getEndDate());
		Assertions.assertEquals(expected.getCourse().getId(), actual.getCourse().getId());
	}

	@Test
	public void saveAndFind() {
		Problem savedProblem = problemRepository.save(sampleProblem);

		em.flush();
		em.clear();

		Problem findProblem = problemRepository.findById(savedProblem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedProblem, findProblem);
	}

	@Test
	public void update() {
		Problem savedProblem = problemRepository.save(sampleProblem);

		em.flush();

		savedProblem.setType(EXAM);
		savedProblem.setTitle("update_junit_test");

		em.flush();
		em.clear();

		Problem findProblem = problemRepository.findById(savedProblem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedProblem, findProblem);
	}

	@Test
	public void delete() {
		Problem savedProblem = problemRepository.save(sampleProblem);

		em.flush();

		problemRepository.delete(savedProblem);

		em.flush();
		em.clear();

		Assertions.assertFalse(problemRepository.findById(savedProblem.getId()).isPresent());
	}
}
