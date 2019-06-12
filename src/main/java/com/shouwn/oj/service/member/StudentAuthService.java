package com.shouwn.oj.service.member;

import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.repository.member.StudentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentAuthService extends MemberAuthService<Student> {

	private final StudentRepository studentRepository;

	public StudentAuthService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
		super(studentRepository, passwordEncoder);
		this.studentRepository = studentRepository;
	}
}
