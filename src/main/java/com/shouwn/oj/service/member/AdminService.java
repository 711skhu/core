package com.shouwn.oj.service.member;

import com.shouwn.oj.repository.member.AdminRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminService extends MemberService {

	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		super(adminRepository);
		this.adminRepository = adminRepository;
	}
}
