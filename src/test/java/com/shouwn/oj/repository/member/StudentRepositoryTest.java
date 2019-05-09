package com.shouwn.oj.repository.member;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(RepositoryTestConfig.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	private Student student;

	@BeforeEach
	public void init() {
		this.student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();
	}

	private void assertEquals(Student student, Student other) {
		if (student != null || other != null) {
			Assertions.assertNotNull(student);
			Assertions.assertNotNull(other);
		}

		Assertions.assertEquals(student.getUsername(), other.getUsername());
		Assertions.assertEquals(student.getPassword(), other.getPassword());
		Assertions.assertEquals(student.getName(), other.getName());
		Assertions.assertEquals(student.getEmail(), other.getEmail());
	}

	@Test
	public void saveAndFind() {
		studentRepository.save(student);

		Student findStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(student, findStudent);
	}

	@Test
	public void update() {
		studentRepository.save(student);

		student.setUsername("update_junit_tester");
		student.setName("update_test123");
		student.setPassword("update_tester");
		student.setEmail("update_test@gmail.com");

		Student findStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(student, findStudent);
	}

	@Test
	public void delete() {
		studentRepository.save(student);

		Student findStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(student, findStudent);

		studentRepository.delete(student);

		Assertions.assertFalse(studentRepository.findById(student.getId()).isPresent());
	}

	@Test
	public void findByUsernameTest() {
		studentRepository.save(student);

		Student findStudent = studentRepository.findByUsername(student.getUsername())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(student, findStudent);

		Assertions.assertFalse(studentRepository.findByUsername("illegalUsername").isPresent());
	}

	@Test
	public void findByEmailTest() {
		studentRepository.save(student);

		Student findStudent = studentRepository.findByEmail(student.getEmail())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(student, findStudent);

		Assertions.assertFalse(studentRepository.findByEmail("illegalEmail").isPresent());
	}
}
