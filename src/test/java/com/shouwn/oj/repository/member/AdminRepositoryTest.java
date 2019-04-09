package com.shouwn.oj.repository.member;

import com.shouwn.oj.model.entity.member.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class AdminRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Test
	public void saveAndFind() {
		Admin admin = Admin.builder()
				.username("junit_tester_1")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(admin);

		Admin findAdmin = adminRepository.findById(admin.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(admin.getUsername(), findAdmin.getUsername());
		Assertions.assertEquals(admin.getPassword(), findAdmin.getPassword());
		Assertions.assertEquals(admin.getName(), findAdmin.getName());
		Assertions.assertEquals(admin.getEmail(), findAdmin.getEmail());
	}

	@Test
	public void update() {
		Admin admin = Admin.builder()
				.username("junit_tester_1")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(admin);

		admin.setName("update_junit_tester_1");
		admin.setPassword("udpate_test123");
		admin.setName("update_tester");
		admin.setEmail("udpate@gamil.com");

		Admin findAdmin = adminRepository.findById(admin.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(admin.getUsername(), findAdmin.getUsername());
		Assertions.assertEquals(admin.getPassword(), findAdmin.getPassword());
		Assertions.assertEquals(admin.getName(), findAdmin.getName());
		Assertions.assertEquals(admin.getEmail(), findAdmin.getEmail());
	}

	@Test
	public void delete() {
		Admin admin = Admin.builder()
				.username("junit_tester_1")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		adminRepository.save(admin);

		Admin findAdmin = adminRepository.findById(admin.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		Assertions.assertEquals(admin.getUsername(), findAdmin.getUsername());
		Assertions.assertEquals(admin.getPassword(), findAdmin.getPassword());
		Assertions.assertEquals(admin.getName(), findAdmin.getName());
		Assertions.assertEquals(admin.getEmail(), findAdmin.getEmail());

		adminRepository.delete(admin);

		Assertions.assertFalse(adminRepository.findById(admin.getId()).isPresent());
	}
}
