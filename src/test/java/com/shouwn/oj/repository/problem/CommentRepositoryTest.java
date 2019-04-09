package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.model.entity.problem.Comment;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.repository.member.AdminRepository;
import com.shouwn.oj.repository.member.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.shouwn.oj.model.enums.ProblemType.PRACTICE;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class CommentRepositoryTest {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProblemDetailRepository problemDetailRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void saveAndFind() {
		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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
				.enabled(false)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Problem problem = Problem.builder()
				.type(PRACTICE)
				.title("junit_test")
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		ProblemDetail problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("test")
				.sequence(1)
				.problem(problem)
				.build();

		problemDetailRepository.save(problemDetail);

		Comment parentComment = Comment.builder()
				.title("junit_test")
				.member(professor)
				.problemDetail(problemDetail)
				.parent(null)
				.build();

		commentRepository.save(parentComment);

		Comment comment = Comment.builder()
				.title("junit_test")
				.member(student)
				.problemDetail(problemDetail)
				.parent(parentComment)
				.build();

		commentRepository.save(comment);

		Comment findComment = commentRepository.findById(comment.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(comment.getMember(), findComment.getMember());
		Assertions.assertEquals(comment.getProblemDetail(), findComment.getProblemDetail());
		Assertions.assertEquals(comment.getParent(), findComment.getParent());
		Assertions.assertEquals(comment.getTitle(), findComment.getTitle());
	}

	@Test
	public void update() {
		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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
				.enabled(false)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Problem problem = Problem.builder()
				.type(PRACTICE)
				.title("junit_test")
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		ProblemDetail problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("test")
				.sequence(1)
				.problem(problem)
				.build();

		problemDetailRepository.save(problemDetail);

		Comment parentComment = Comment.builder()
				.title("junit_test")
				.member(professor)
				.problemDetail(problemDetail)
				.parent(null)
				.build();

		commentRepository.save(parentComment);

		Comment comment = Comment.builder()
				.title("junit_test")
				.member(student)
				.problemDetail(problemDetail)
				.parent(parentComment)
				.build();

		commentRepository.save(comment);

		comment.setTitle("update_junit_test");

		Comment findComment = commentRepository.findById(comment.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(comment.getMember(), findComment.getMember());
		Assertions.assertEquals(comment.getProblemDetail(), findComment.getProblemDetail());
		Assertions.assertEquals(comment.getParent(), findComment.getParent());
		Assertions.assertEquals(comment.getTitle(), findComment.getTitle());
	}

	@Test
	public void delete() {
		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

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
				.enabled(false)
				.professor(professor)
				.build();

		courseRepository.save(newCourse);

		Problem problem = Problem.builder()
				.type(PRACTICE)
				.title("junit_test")
				.course(newCourse)
				.build();

		problemRepository.save(problem);

		ProblemDetail problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("test")
				.sequence(1)
				.problem(problem)
				.build();

		problemDetailRepository.save(problemDetail);

		Comment parentComment = Comment.builder()
				.title("junit_test")
				.member(professor)
				.problemDetail(problemDetail)
				.build();

		commentRepository.save(parentComment);

		Comment comment = Comment.builder()
				.title("junit_test")
				.member(student)
				.problemDetail(problemDetail)
				.parent(parentComment)
				.build();

		commentRepository.save(comment);

		Comment findComment = commentRepository.findById(comment.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(comment.getMember(), findComment.getMember());
		Assertions.assertEquals(comment.getProblemDetail(), findComment.getProblemDetail());
		Assertions.assertEquals(comment.getParent(), findComment.getParent());
		Assertions.assertEquals(comment.getTitle(), findComment.getTitle());

		commentRepository.delete(comment);

		Assertions.assertFalse(commentRepository.findById(comment.getId()).isPresent());
	}
}
