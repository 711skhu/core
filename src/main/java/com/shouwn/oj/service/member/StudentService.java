package com.shouwn.oj.service.member;

import com.shouwn.oj.model.entity.member.Student;
import com.shouwn.oj.repository.member.StudentRepository;

import org.springframework.stereotype.Service;

@Service
public class StudentService extends MemberService<Student> {

	private final StudentRepository adminRepository;

	public StudentService(StudentRepository studentRepository) {
		super(studentRepository);
		this.adminRepository = studentRepository;
	}
}
