package com.shouwn.oj.repository.member;

import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private EntityManager em;

	private Student sampleStudent;

	@BeforeEach
	public void init() {
		this.sampleStudent = Student.builder()
				.name("tester_student")
				.username("junit_tester_student")
				.password("test123")
				.email("test.student@gmail.com")
				.build();
	}

	private void assertEquals(Student expected, Student actual) {
		Assertions.assertEquals(expected.getUsername(), actual.getUsername());
		Assertions.assertEquals(expected.getPassword(), actual.getPassword());
		Assertions.assertEquals(expected.getName(), actual.getName());
		Assertions.assertEquals(expected.getEmail(), actual.getEmail());
	}

	@Test
	public void saveAndFind() {
		Student savedStudent = studentRepository.save(sampleStudent);

		em.flush();
		em.clear();

		Student findStudent = studentRepository.findById(savedStudent.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedStudent, findStudent);
	}

	@Test
	public void update() {
		Student savedStudent = studentRepository.save(sampleStudent);

		em.flush();

		savedStudent.setName("update_test123");
		savedStudent.setPassword("update_tester");
		savedStudent.setEmail("update_test@gmail.com");

		em.flush();
		em.clear();

		Student findStudent = studentRepository.findById(savedStudent.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedStudent, findStudent);
	}

	@Test
	public void delete() {
		Student savedStudent = studentRepository.save(sampleStudent);

		em.flush();

		studentRepository.delete(savedStudent);

		em.flush();
		em.clear();

		Assertions.assertFalse(studentRepository.findById(savedStudent.getId()).isPresent());
	}

	@Test
	public void findByUsernameTest() {
		Student savedStudent = studentRepository.save(sampleStudent);

		em.flush();
		em.clear();

		Student findStudent = studentRepository.findByUsername(savedStudent.getUsername())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedStudent, findStudent);

		Assertions.assertFalse(studentRepository.findByUsername("illegalUsername").isPresent());
	}

	@Test
	public void findByEmailTest() {
		Student savedStudent = studentRepository.save(sampleStudent);

		em.flush();
		em.clear();

		Student findStudent = studentRepository.findByEmail(savedStudent.getEmail())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(savedStudent, findStudent);

		Assertions.assertFalse(studentRepository.findByEmail("illegalEmail").isPresent());
	}
}
