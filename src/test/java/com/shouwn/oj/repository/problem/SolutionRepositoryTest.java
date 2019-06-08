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

	@Test
	public void saveAndFind() {
		Admin professor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(professor);

		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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

		Solution solution = Solution.builder()
				.content("junit_test")
				.score(90)
				.member(student)
				.problemDetail(problemDetail)
				.build();

		solutionRepository.save(solution);

		Solution findSolution = solutionRepository.findById(solution.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(solution.getContent(), findSolution.getContent());
		Assertions.assertEquals(solution.getScore(), findSolution.getScore());
		Assertions.assertEquals(solution.getMember(), findSolution.getMember());
		Assertions.assertEquals(solution.getProblemDetail(), findSolution.getProblemDetail());
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

		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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

		Solution solution = Solution.builder()
				.content("junit_test")
				.score(90)
				.member(student)
				.problemDetail(problemDetail)
				.build();

		solutionRepository.save(solution);

		solution.setContent("update_junit_test");
		solution.setScore(84);

		Solution findSolution = solutionRepository.findById(solution.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(solution.getContent(), findSolution.getContent());
		Assertions.assertEquals(solution.getScore(), findSolution.getScore());
		Assertions.assertEquals(solution.getMember(), findSolution.getMember());
		Assertions.assertEquals(solution.getProblemDetail(), findSolution.getProblemDetail());
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

		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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

		Solution solution = Solution.builder()
				.content("junit_test")
				.score(90)
				.member(student)
				.problemDetail(problemDetail)
				.build();

		solutionRepository.save(solution);

		Solution findSolution = solutionRepository.findById(solution.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(solution.getContent(), findSolution.getContent());
		Assertions.assertEquals(solution.getScore(), findSolution.getScore());
		Assertions.assertEquals(solution.getMember(), findSolution.getMember());
		Assertions.assertEquals(solution.getProblemDetail(), findSolution.getProblemDetail());

		solutionRepository.delete(solution);

		Assertions.assertFalse(solutionRepository.findById(solution.getId()).isPresent());
	}

	@Test
	public void findSolutionsByProblemDetailOrderById() {
		Admin professor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(professor);

		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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

		Solution solution1 = Solution.builder()
				.content("junit_test")
				.score(1)
				.member(student)
				.problemDetail(problemDetail)
				.build();

		solutionRepository.save(solution1);

		Solution solution2 = Solution.builder()
				.content("junit_test")
				.score(1)
				.member(student)
				.problemDetail(problemDetail)
				.build();

		Solution s = solutionRepository.save(solution2);

		problemDetail.getSolutions().add(solution1);
		problemDetail.getSolutions().add(solution2);

		problem.getProblemDetails().add(problemDetail);

		newCourse.getProblems().add(problem);

		student.getSolutions().add(solution1);
		student.getSolutions().add(solution2);

		professor.getCourses().add(newCourse);

		List<Solution> solutions = solutionRepository.findSolutionsByProblemDetailOrderByIdDesc(problemDetail);

		Assertions.assertEquals(s, solutions.get(0));
	}
}
