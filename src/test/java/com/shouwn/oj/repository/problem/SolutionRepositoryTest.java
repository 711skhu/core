package com.shouwn.oj.repository.problem;

import java.util.List;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.Solution;
import com.shouwn.oj.repository.member.AdminRepository;
import com.shouwn.oj.repository.member.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@ExtendWith(SpringExtension.class)
@Import(RepositoryTestConfig.class)
@DataJpaTest
public class SolutionRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProblemDetailRepository problemDetailRepository;

	@Autowired
	private SolutionRepository solutionRepository;

	private Admin professor;

	private Student student;

	private Course course;

	private Problem problem;

	private ProblemDetail problemDetail;

	private Solution solution;

	@BeforeEach
	void init() {
		this.professor = Admin.builder()
				.username("test_professor")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		this.professor = this.adminRepository.save(this.professor);

		this.course = Course.builder()
				.name("junit_test")
				.description("test")
				.enabled(true)
				.professor(this.professor)
				.build();

		this.course = this.courseRepository.save(this.course);

		this.professor.getCourses().add(this.course);

		this.student = Student.builder()
				.username("test_student")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		this.student.getCourses().add(this.course);
		this.student = this.studentRepository.save(this.student);

		this.course.getStudents().add(this.student);

		this.problem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test")
				.course(this.course)
				.build();

		this.problem = this.problemRepository.save(this.problem);

		this.course.getProblems().add(this.problem);

		this.problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("junit-test")
				.sequence(1)
				.problem(this.problem)
				.build();

		this.problemDetail = this.problemDetailRepository.save(this.problemDetail);

		this.problem.getProblemDetails().add(this.problemDetail);

		this.solution = Solution.builder()
				.content("junit_test")
				.score(2)
				.member(this.student)
				.problemDetail(this.problemDetail)
				.build();

		this.solution = this.solutionRepository.save(solution);

		this.student.getSolutions().add(this.solution);
		this.problemDetail.getSolutions().add(this.solution);
	}

	@Test
	public void findById() {
		Solution findSolution = this.solutionRepository.findById(this.solution.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(this.solution.getContent(), findSolution.getContent());
		Assertions.assertEquals(this.solution.getScore(), findSolution.getScore());
		Assertions.assertEquals(this.solution.getMember(), findSolution.getMember());
		Assertions.assertEquals(this.solution.getProblemDetail(), findSolution.getProblemDetail());
	}

	@Test
	public void update() {
		Long idBeforeUpdate = this.solution.getId();
		int scoreBeforeUpdate = this.solution.getScore();

		this.solution.setScore(4);

		Solution s = this.solutionRepository.save(this.solution);

		Assertions.assertEquals(idBeforeUpdate, s.getId());
		Assertions.assertNotEquals(scoreBeforeUpdate, s.getScore());
	}

	@Test
	public void delete() {
		this.solutionRepository.delete(this.solution);

		Assertions.assertFalse(this.solutionRepository.findById(this.solution.getId()).isPresent());
	}

	@Test
	public void findSolutionsByProblemDetailAndMember() {
		List<Solution> solutions = this.solutionRepository.findSolutionsByProblemDetailAndMember(this.problemDetail, this.student);

		Assertions.assertEquals(this.solution, solutions.get(0));
	}
}
