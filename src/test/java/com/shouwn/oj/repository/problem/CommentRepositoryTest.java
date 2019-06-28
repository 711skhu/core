package com.shouwn.oj.repository.problem;

import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.model.entity.problem.Comment;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.shouwn.oj.model.enums.ProblemType.PRACTICE;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private EntityManager em;

	private Comment sampleProfessorComment;

	private Comment sampleStudentComment;

	@BeforeEach
	public void init() {
		Student savedStudent = Student.builder()
				.name("tester_student")
				.username("junit_tester_student")
				.password("test123")
				.email("test.student@gmail.com")
				.build();

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

		Problem savedProblem = Problem.builder()
				.type(PRACTICE)
				.title("junit_test_problem")
				.course(savedCourse)
				.build();

		savedCourse.getProblems().add(savedProblem);

		ProblemDetail savedProblemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("test")
				.sequence(1)
				.problem(savedProblem)
				.build();

		savedProblem.getProblemDetails().add(savedProblemDetail);

		em.persist(savedStudent);
		em.persist(savedProfessor);
		em.persist(savedCourse);
		em.persist(savedProblem);
		em.persist(savedProblemDetail);

		sampleStudentComment = Comment.builder()
				.title("junit_test_student_comment")
				.member(savedStudent)
				.problemDetail(savedProblemDetail)
				.build();

		sampleProfessorComment = Comment.builder()
				.title("junit_test_professor_comment")
				.member(savedProfessor)
				.problemDetail(savedProblemDetail)
				.build();
	}

	private void assertEquals(Comment expected, Comment actual) {
		Assertions.assertEquals(expected.getTitle(), actual.getTitle());
		Assertions.assertEquals(expected.getMember().getId(), actual.getMember().getId());
		Assertions.assertEquals(expected.getProblemDetail().getId(), actual.getProblemDetail().getId());

		if (expected.getParent() != null) {
			Assertions.assertNotNull(actual.getParent());
			Assertions.assertEquals(expected.getParent().getId(), actual.getParent().getId());
		} else {
			Assertions.assertNull(actual.getParent());
		}
	}

	@Test
	public void saveAndFind() {
		Comment savedStudentComment =  commentRepository.save(sampleStudentComment);
		Comment savedProfessorComment = commentRepository.save(sampleProfessorComment);

		savedProfessorComment.setParent(savedStudentComment);
		savedStudentComment.getChildren().add(savedProfessorComment);

		em.flush();
		em.clear();

		Comment findStudentComment = commentRepository.findById(savedStudentComment.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Comment findProfessorComment = commentRepository.findById(savedProfessorComment.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertNotNull(findProfessorComment.getParent());
		Assertions.assertFalse(findStudentComment.getChildren().isEmpty());

		assertEquals(savedStudentComment, findStudentComment);
		assertEquals(savedProfessorComment, findProfessorComment);
	}

	@Test
	public void update() {
		Comment savedComment = commentRepository.save(sampleStudentComment);

		em.flush();

		savedComment.setTitle("update_junit_test_comment");

		em.flush();
		em.clear();

		Comment findComment = commentRepository.findById(savedComment.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedComment, findComment);
	}

	@Test
	public void delete() {
		Comment savedComment = commentRepository.save(sampleStudentComment);

		em.flush();

		commentRepository.delete(savedComment);

		em.flush();
		em.clear();

		Assertions.assertFalse(commentRepository.findById(savedComment.getId()).isPresent());
	}

	@Test
	public void notPossibleDeleteIfHaveChildren() {
		Comment savedStudentComment =  commentRepository.save(sampleStudentComment);
		Comment savedProfessorComment = commentRepository.save(sampleProfessorComment);

		savedProfessorComment.setParent(savedStudentComment);
		savedStudentComment.getChildren().add(savedProfessorComment);

		em.flush();

		Assertions.assertThrows(IllegalStateException.class, () ->
				commentRepository.delete(savedStudentComment));
	}
}
