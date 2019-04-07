package com.shouwn.oj.repository.problem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.repository.member.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.shouwn.oj.model.enums.ProblemType.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class ProblemReposirotyTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ProblemRepository problemRepository;

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
				.startDate(LocalDateTime.parse("2019-04-07 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.endDate(LocalDateTime.parse("2019-04-10 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		Problem findProblem = problemRepository.findById(problem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problem.getType(), findProblem.getType());
		Assertions.assertEquals(problem.getTitle(), findProblem.getTitle());
		Assertions.assertEquals(problem.getStartDate(), findProblem.getStartDate());
		Assertions.assertEquals(problem.getEndDate(), findProblem.getEndDate());
		Assertions.assertEquals(problem.getCourse(), findProblem.getCourse());
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
				.startDate(LocalDateTime.parse("2019-04-07 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.endDate(LocalDateTime.parse("2019-04-10 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		problem.setType(EXAM);
		problem.setTitle("update_junit_test");
		problem.setStartDate(LocalDateTime.parse("2019-04-07 14:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		problem.setEndDate(LocalDateTime.parse("2019-04-11 14:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		Problem findProblem = problemRepository.findById(problem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problem.getType(), findProblem.getType());
		Assertions.assertEquals(problem.getTitle(), findProblem.getTitle());
		Assertions.assertEquals(problem.getStartDate(), findProblem.getStartDate());
		Assertions.assertEquals(problem.getEndDate(), findProblem.getEndDate());
		Assertions.assertEquals(problem.getCourse(), findProblem.getCourse());
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
				.startDate(LocalDateTime.parse("2019-04-07 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.endDate(LocalDateTime.parse("2019-04-10 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		Problem findProblem = problemRepository.findById(problem.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(problem.getType(), findProblem.getType());
		Assertions.assertEquals(problem.getTitle(), findProblem.getTitle());
		Assertions.assertEquals(problem.getStartDate(), findProblem.getStartDate());
		Assertions.assertEquals(problem.getEndDate(), findProblem.getEndDate());
		Assertions.assertEquals(problem.getCourse(), findProblem.getCourse());

		problemRepository.delete(problem);

		Assertions.assertFalse(problemRepository.findById(problem.getId()).isPresent());
	}
}
