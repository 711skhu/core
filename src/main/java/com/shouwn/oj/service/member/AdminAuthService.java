package com.shouwn.oj.service.member;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.repository.member.AdminRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService extends MemberAuthService<Admin> {

	private final AdminRepository adminRepository;

	public AdminAuthService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		super(adminRepository, passwordEncoder);
		this.adminRepository = adminRepository;
	}
}
