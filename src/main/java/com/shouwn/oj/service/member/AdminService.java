package com.shouwn.oj.service.member;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.repository.member.AdminRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminService extends MemberService<Admin> {

	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		super(adminRepository);
		this.adminRepository = adminRepository;
	}
}
