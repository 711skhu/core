package com.shouwn.oj.service.member;

import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.repository.member.StudentRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService implements MemberAuthService<Student>, MemberService<Student> {

	private final StudentRepository studentRepository;

	private final PasswordEncoder passwordEncoder;

	public StudentService(StudentRepository studentRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 학생을 생성하는 메소드
	 *
	 * @param name        학생 이름
	 * @param username    학생 아이디
	 * @param rawPassword 학생 패스워드 (인코딩 전)
	 * @param email       학생 이메일
	 * @return 생성된 관리자 객체
	 */

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Student makeStudent(String name,
							   String username,
							   String rawPassword,
							   String email) {
		Student student = Student.builder()
				.name(name)
				.username(username)
				.password(rawPassword)
				.email(email)
				.build();

		checkPossibleToMakeMember(student);

		student.setPassword(passwordEncoder.encode(student.getPassword()));

		return studentRepository.save(student);
	}

	/**
	 * 학생 계정을 생성할 수 있는지 확인하는 메소드
	 *
	 * @param student 생성할 학생 객체
	 */

	@Override
	public void checkPossibleToMakeMember(Student student) {
		MemberAuthService.super.checkPossibleToMakeMember(student);

		// "student" 회원가입을 위해서 추가로 확인해야 할 작업
	}

	@Override
	public Optional<Student> findById(Long id) {
		return studentRepository.findById(id);
	}

	@Override
	public Optional<Student> findByUsername(String username) {
		return studentRepository.findByUsername(username);
	}

	@Override
	public Optional<Student> findByEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	@Override
	public boolean isCorrectPassword(Member member, String rawPassword) {
		return passwordEncoder.matches(rawPassword, member.getPassword());
	}
}
