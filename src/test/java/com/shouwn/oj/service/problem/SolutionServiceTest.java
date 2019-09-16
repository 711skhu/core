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
class SolutionServiceTest {

	@Mock
	private SolutionRepository solutionRepository;

	private SolutionService solutionService;

	private Student student;

	private ProblemDetail problemDetail;

	private Solution solution;

	@BeforeEach
	void init() {
		this.solutionService = new SolutionService(this.solutionRepository);

		Admin professor = Admin.builder()
				.name("test_professor")
				.username("test_professor")
				.password("test1234")
				.email("test@skhu.ac.kr")
				.build();

		Course course = Course.builder()
				.name("test")
				.description("test")
				.professor(professor)
				.build();

		course.setEnabled(true);

		professor.getCourses().add(course);

		this.student = Student.builder()
				.name("test_student")
				.username("test_student")
				.password("test1234")
				.email("tst@skhu.ac.kr")
				.build();

		CourseRegister register = CourseRegister.builder()
				.course(course)
				.student(student)
				.build();

		this.student.getRegisters().add(register);
		course.getRegisters().add(register);

		Problem problem = Problem.builder()
				.title("test")
				.type(ProblemType.PRACTICE)
				.course(course)
				.build();

		course.getProblems().add(problem);

		this.problemDetail = ProblemDetail.builder()
				.title("test1")
				.sequence(1)
				.content("test")
				.problem(problem)
				.build();

		problem.getProblemDetails().add(this.problemDetail);

		TestCase testCase = TestCase.builder()
				.params("1")
				.result("1")
				.problemDetail(this.problemDetail)
				.build();

		this.problemDetail.getTestCases().add(testCase);

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
