package com.shouwn.oj.service.member;

import com.shouwn.oj.exception.member.*;
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
	 * @param name		  학생 이름
	 * @param username	  학생 아이디
	 * @param rawPassword 학생 패스워드 (인코딩 전)
	 * @param email		  학생 이메일
	 *
	 * @return 생성된 관리자 객체
	 *
	 * @throws MemberException UsernameExistException 이미 아이디가 존재할 때 발생하는 예외
	 * 	                       PasswordStrengthLeakException 비밀번호가 약할 때 발생하는 예외
	 * 	                       EmailExistException 이메일이 이미 존재할 때 발생하는 예외
	 */

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Student makeStudent(String name,
							   String username,
							   String rawPassword,
							   String email) throws MemberException {

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
	 *
	 * @throws UsernameExistException        아이디가 이미 등록되었을 때 발생하는 예외
	 * @throws PasswordStrengthLeakException 비밀번호 강도가 약할 때 발생하는 예외
	 * @throws EmailExistException           이메일이 이미 등록되었을 때 발생하는 예외
	 */

	@Override
	public void checkPossibleToMakeMember(Student student)
			throws UsernameExistException, PasswordStrengthLeakException, EmailExistException {
		MemberAuthService.super.checkPossibleToMakeMember(student);

		// "student" 회원가입을 위해서 추가로 확인해야 할 작업
	}

	@Override
	public Student findById(Long id) throws IdNotExistException {
		return studentRepository.findById(id)
				.orElseThrow(() -> new IdNotExistException("User not found with id"));
	}

	@Override
	public Student findByUsername(String username) throws UsernameNotExistException {
		return studentRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotExistException("User not found with username"));
	}

	@Override
	public Student findByEmail(String email) throws EmailNotExistException {
		return studentRepository.findByEmail(email)
				.orElseThrow(() -> new EmailNotExistException("User not found with email"));
	}

	@Override
	public boolean isCorrectPassword(Member member, String rawPassword) {
		return passwordEncoder.matches(rawPassword, member.getPassword());
	}
}
