package com.shouwn.oj.service.member;

import java.util.Optional;

import com.shouwn.oj.exception.member.EmailExistException;
import com.shouwn.oj.exception.member.PasswordStrengthLeakException;
import com.shouwn.oj.exception.member.UsernameExistException;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.repository.member.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudentServiceTest {

	@Mock
	private StudentRepository studentRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	private StudentService studentService;

	private Student student;

	@BeforeEach
	void init() {
		this.studentService = new StudentService(this.studentRepository, this.passwordEncoder);

		this.student = Student.builder()
				.name("test")
				.username("test")
				.password("test12345")
				.email("test@skhu.ac.kr")
				.build();
	}

	@Test
	public void makeStudentSuccess() {
		String encodedPassword = "encoded";

		when(studentRepository.findByUsername(any())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(any())).thenReturn(encodedPassword);

		studentService.makeStudent(this.student.getName(),
				this.student.getUsername(),
				this.student.getPassword(),
				this.student.getEmail());

		final ArgumentCaptor<Student> saveCaptor = ArgumentCaptor.forClass(Student.class);

		verify(studentRepository).save(saveCaptor.capture());
		verify(passwordEncoder).encode(this.student.getPassword());

		assertEquals(this.student.getUsername(), saveCaptor.getValue().getUsername());
		assertEquals(encodedPassword, saveCaptor.getValue().getPassword());
	}

	@Test
	public void makeStudentThrowsUsernameExistException() {
		when(studentRepository.findByUsername(any())).thenReturn(Optional.of(this.student));

		assertThrows(UsernameExistException.class,
				() -> studentService.makeStudent(this.student.getName(),
						this.student.getUsername(),
						this.student.getPassword(),
						this.student.getEmail())
		);

		verify(studentRepository).findByUsername(this.student.getUsername());
	}

	@Test
	public void makeStudentThrowsEmailExistException() {
		when(studentRepository.findByUsername(any())).thenReturn(Optional.empty());
		when(studentRepository.findByEmail(any())).thenReturn(Optional.of(this.student));

		assertThrows(EmailExistException.class,
				() -> studentService.makeStudent(this.student.getName(),
						this.student.getUsername(),
						this.student.getPassword(),
						this.student.getEmail())
		);

		verify(studentRepository).findByUsername(this.student.getUsername());
		verify(studentRepository).findByEmail(this.student.getEmail());
	}

	@Test
	public void makeStudentThrowsPasswordStrengthLeakException() {
		when(studentRepository.findByUsername(any())).thenReturn(Optional.empty());

		this.student.setPassword("test123");

		assertThrows(PasswordStrengthLeakException.class,
				() -> studentService.makeStudent(this.student.getName(),
						this.student.getUsername(),
						this.student.getPassword(),
						this.student.getEmail())
		);

		verify(studentRepository).findByUsername(this.student.getUsername());
	}
}