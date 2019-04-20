package com.shouwn.oj.service.member;

import com.shouwn.oj.exception.member.MemberException;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.repository.member.AdminRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminService extends MemberService {

	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		super(adminRepository);
		this.adminRepository = adminRepository;
	}

	public Admin makeAdmin(Admin admin) throws MemberException {
		checkPossibleToMakeAdmin(admin);

		return adminRepository.save(admin);
	}

	private void checkPossibleToMakeAdmin(Admin admin) throws MemberException {
		checkPossibleToMakeMember(admin);

		// "admin" 이 회원가입 하기 위해서 추가로 체크해야 하는 작업들
	}
}
