package com.shouwn.oj.service.problem;

import java.util.Collections;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.model.entity.problem.*;
import com.shouwn.oj.model.enums.ProblemType;
import com.shouwn.oj.repository.problem.SolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SolutionServiceTest {

	@Mock
	private SolutionRepository solutionRepository;

	private SolutionService solutionService;

	private Student student;

	private Admin professor;

	private Course course;

	private Problem problem;

	private ProblemDetail problemDetail;

	private TestCase testCase;

	private Solution solution;

	@BeforeEach
	void init() {
		this.solutionService = new SolutionService(this.solutionRepository);

		this.professor = Admin.builder()
				.name("test_professor")
				.username("test_professor")
				.password("test1234")
				.email("test@skhu.ac.kr")
				.build();

		this.course = Course.builder()
				.name("test")
				.description("test")
				.enabled(true)
				.professor(this.professor)
				.build();

		this.professor.getCourses().add(this.course);

		this.student = Student.builder()
				.name("test_student")
				.username("test_student")
				.password("test1234")
				.email("tst@skhu.ac.kr")
				.build();

		this.student.getCourses().add(this.course);
		this.course.getStudents().add(this.student);

		this.problem = Problem.builder()
				.title("test")
				.type(ProblemType.PRACTICE)
				.course(this.course)
				.build();

		this.course.getProblems().add(this.problem);

		this.problemDetail = ProblemDetail.builder()
				.title("test1")
				.sequence(1)
				.content("test")
				.problem(this.problem)
				.build();

		this.problem.getProblemDetails().add(this.problemDetail);

		this.testCase = TestCase.builder()
				.params("1")
				.result("1")
				.problemDetail(this.problemDetail)
				.build();

		this.problemDetail.getTestCases().add(this.testCase);

		this.solution = Solution.builder()
				.content("test")
				.score(1)
				.member(this.student)
				.problemDetail(this.problemDetail)
				.build();

		this.problemDetail.getSolutions().add(this.solution);
		this.student.getSolutions().add(this.solution);
	}

	@Test
	void getStudentScoreInProblemDetailsSuccess() {
		when(solutionRepository.findSolutionsByProblemDetailAndMember(any(), any())).thenReturn(Collections.singletonList(this.solution));

		int result = solutionService.getStudentScoreInProblemDetails(this.student, Collections.singletonList(this.problemDetail));

		verify(solutionRepository).findSolutionsByProblemDetailAndMember(this.problemDetail, this.student);

		assertEquals(1, result);
	}
}
