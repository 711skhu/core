package com.shouwn.oj.service.member;

import javax.persistence.EntityNotFoundException;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.repository.member.AdminRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AdminService extends MemberService {

	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public Admin findAdminById(Long id) {
		return adminRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
