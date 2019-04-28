package com.shouwn.oj.service.member;

import com.shouwn.oj.exception.member.EmailNotExistException;
import com.shouwn.oj.exception.member.IdNotExistException;
import com.shouwn.oj.exception.member.UsernameNotExistException;
import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.repository.member.StudentRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements MemberAuthService<Student>, MemberService<Student> {

	private final StudentRepository studentRepository;

	private final PasswordEncoder passwordEncoder;

	public StudentService(StudentRepository studentRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
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
