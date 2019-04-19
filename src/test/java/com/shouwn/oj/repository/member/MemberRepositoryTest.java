package com.shouwn.oj.repository.member;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
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
public class MemberRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void saveAndFind() {
		Admin admin = Admin.builder()
				.username("junit_tester_1")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(admin);

		Student student = Student.builder()
				.username("junit_tester_1")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		studentRepository.save(student);

		Assertions.assertTrue(memberRepository.findById(admin.getId()).isPresent());

		Assertions.assertTrue(memberRepository.findById(student.getId()).isPresent());
	}
}
