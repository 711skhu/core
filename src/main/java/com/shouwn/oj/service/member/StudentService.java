package com.shouwn.oj.service.member;

import com.shouwn.oj.repository.member.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StudentService extends MemberService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		super(studentRepository);
		this.studentRepository = studentRepository;
	}
}
