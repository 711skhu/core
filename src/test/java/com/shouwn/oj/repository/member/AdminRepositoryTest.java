package com.shouwn.oj.repository.member;

import javax.persistence.EntityManager;

import com.shouwn.oj.config.repository.RepositoryTestConfig;
import com.shouwn.oj.model.entity.member.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(RepositoryTestConfig.class)
@DataJpaTest
public class AdminRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private EntityManager em;

	private Admin sampleAdmin;

	@BeforeEach
	public void init() {
		sampleAdmin = Admin.builder()
				.name("tester_admin")
				.username("junit_tester_admin")
				.password("test123")
				.email("test@gmail.com")
				.build();
	}

	private void assertEquals(Admin expected, Admin actual) {
		Assertions.assertEquals(expected.getUsername(), actual.getUsername());
		Assertions.assertEquals(expected.getPassword(), actual.getPassword());
		Assertions.assertEquals(expected.getName(), actual.getName());
		Assertions.assertEquals(expected.getEmail(), actual.getEmail());
	}

	@Test
	public void saveAndFind() {
		Admin savedAdmin = adminRepository.save(sampleAdmin);

		em.flush();
		em.clear();

		Admin findAdmin = adminRepository.findById(savedAdmin.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(sampleAdmin, findAdmin);
	}

	@Test
	public void update() {
		Admin savedAdmin = adminRepository.save(sampleAdmin);

		em.flush();

		savedAdmin.setPassword("udpate_test123");
		savedAdmin.setName("update_tester");
		savedAdmin.setEmail("udpate@gamil.com");

		em.flush();
		em.clear();

		Admin findAdmin = adminRepository.findById(savedAdmin.getId())
				.orElseThrow(() -> new RuntimeException("찾을 수 없습니다."));

		assertEquals(sampleAdmin, findAdmin);
	}

	@Test
	public void delete() {
		Admin savedAdmin = adminRepository.save(sampleAdmin);

		em.flush();

		adminRepository.delete(savedAdmin);

		Assertions.assertFalse(adminRepository.findById(savedAdmin.getId()).isPresent());
	}
}
