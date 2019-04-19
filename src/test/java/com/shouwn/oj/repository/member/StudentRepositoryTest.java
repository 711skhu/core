package com.shouwn.oj.repository.member;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Student;
import org.junit.jupiter.api.Assertions;
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

	@Test
	public void saveAndFind() {
		Student student = Student.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

		Student findStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(student.getUsername(), findStudent.getUsername());
		Assertions.assertEquals(student.getPassword(), findStudent.getPassword());
		Assertions.assertEquals(student.getName(), findStudent.getName());
		Assertions.assertEquals(student.getEmail(), findStudent.getEmail());
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

		student.setUsername("update_junit_tester");
		student.setName("update_test123");
		student.setPassword("update_tester");
		student.setEmail("update_test@gmail.com");

		Student findStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(student.getUsername(), findStudent.getUsername());
		Assertions.assertEquals(student.getPassword(), findStudent.getPassword());
		Assertions.assertEquals(student.getName(), findStudent.getName());
		Assertions.assertEquals(student.getEmail(), findStudent.getEmail());
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

		Student findStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(student.getUsername(), findStudent.getUsername());
		Assertions.assertEquals(student.getPassword(), findStudent.getPassword());
		Assertions.assertEquals(student.getName(), findStudent.getName());
		Assertions.assertEquals(student.getEmail(), findStudent.getEmail());

		studentRepository.delete(student);

		Assertions.assertFalse(studentRepository.findById(student.getId()).isPresent());
	}
}
