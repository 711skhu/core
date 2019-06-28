package com.shouwn.oj.repository.problem;

import java.util.List;
import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.model.entity.problem.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class SolutionRepositoryTest {

	@Autowired
	private SolutionRepository solutionRepository;

	@Autowired
	private EntityManager em;

	private Solution sampleSolution;

	public SolutionRepositoryTest() {
	}

	@BeforeEach
	void init() {
		Admin savedProfessor = Admin.builder()
				.name("tester_professor")
				.username("test_professor")
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

		Student savedStudent = Student.builder()
				.name("tester_student")
				.username("test_student")
				.password("test123")
				.email("test.student@gmail.com")
				.build();

		CourseRegister register = CourseRegister.builder()
				.student(savedStudent)
				.course(savedCourse)
				.build();

		savedStudent.getRegisters().add(register);
		savedCourse.getRegisters().add(register);

		ProblemDetail savedProblemDetail = ProblemDetail.builder()
				.title("junit_test_detail")
				.content("junit-test")
				.sequence(1)
				.problem(savedProblem)
				.build();

		savedProblem.getProblemDetails().add(savedProblemDetail);

		sampleSolution = Solution.builder()
				.content("public static void main() { }")
				.score(10)
				.member(savedStudent)
				.problemDetail(savedProblemDetail)
				.build();

		em.persist(savedProfessor);
		em.persist(savedStudent);
		em.persist(savedCourse);
		em.persist(savedProblem);
		em.persist(savedProblemDetail);

		savedProblemDetail.getSolutions().add(sampleSolution);
		savedStudent.getSolutions().add(sampleSolution);
	}

	private void assertEquals(Solution expected, Solution actual) {
		Assertions.assertEquals(expected.getContent(), actual.getContent());
		Assertions.assertEquals(expected.getScore(), actual.getScore());
		Assertions.assertEquals(expected.getMember().getId(), actual.getMember().getId());
		Assertions.assertEquals(expected.getProblemDetail().getId(), actual.getProblemDetail().getId());
	}

	@Test
	public void saveAndFind() {
		Solution savedSolution = solutionRepository.save(sampleSolution);

		em.flush();
		em.clear();

		Solution findSolution = this.solutionRepository.findById(savedSolution.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedSolution, findSolution);
	}

	@Test
	public void update() {
		// solution 은 수정되면 안 됨
	}

	@Test
	public void delete() {
		Solution savedSolution = solutionRepository.save(sampleSolution);

		em.flush();

		this.solutionRepository.delete(savedSolution);

		em.flush();
		em.clear();

		Assertions.assertFalse(this.solutionRepository.findById(savedSolution.getId()).isPresent());
	}

	@Test
	public void findSolutionsByProblemDetailAndMember() {
		Member sampleMember = sampleSolution.getMember();
		ProblemDetail sampleProblemDetail = sampleSolution.getProblemDetail();

		List<Solution> beforeSolutions = this.solutionRepository.findSolutionsByProblemDetailAndMember(sampleProblemDetail, sampleMember);

		int beforeSaveCount = beforeSolutions.size();

		Solution savedSolution = solutionRepository.save(sampleSolution);

		Member savedMember = savedSolution.getMember();
		ProblemDetail savedProblemDetail = savedSolution.getProblemDetail();

		em.flush();
		em.clear();

		List<Solution> afterSolutions = this.solutionRepository.findSolutionsByProblemDetailAndMember(savedProblemDetail, savedMember);

		Assertions.assertEquals(beforeSaveCount + 1, afterSolutions.size());
	}
}
