package com.shouwn.oj.service.member;

import com.shouwn.oj.exception.member.*;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.repository.member.AdminRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService implements MemberAuthService<Admin>, MemberService<Admin> {

	private final AdminRepository adminRepository;

	private final PasswordEncoder passwordEncoder;

	public AdminService(AdminRepository adminRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 관리자를 생성하는 메소드
	 *
	 * @param name        관리자 이름
	 * @param username    관리자 아이디
	 * @param rawPassword 관리자 패스워드 (인코딩 되지 않은)
	 * @param email       관리자 이메일
	 * @return 생성된 관리자 객체
	 * @throws MemberException UsernameExistException 이미 아이디가 존재할 때 발생하는 예외
	 *                         PasswordStrengthLeakException 비밀번호가 약할 때 발생하는 예외
	 *                         EmailExistException 이메일이 이미 존재할 때 발생하는 예외
	 */
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Admin makeAdmin(String name,
						   String username,
						   String rawPassword,
						   String email) throws MemberException {

		Admin admin = Admin.builder()
				.name(name)
				.username(username)
				.password(rawPassword)
				.email(email)
				.build();

		checkPossibleToMakeMember(admin);

		admin.setPassword(passwordEncoder.encode(admin.getPassword()));

		return adminRepository.save(admin);
	}

	/**
	 * 관리자 계정을 만들 수 있는지 체크하는 메소드
	 *
	 * @param admin 만들 관리자 객체
	 * @throws UsernameExistException        아이디가 이미 등록되었을 때 발생하는 예외
	 * @throws PasswordStrengthLeakException 비밀번호 강도가 약할 때 발생하는 예외
	 * @throws EmailExistException           이메일이 이미 등록되었을 때 발생하는 예외
	 */
	@Override
	public void checkPossibleToMakeMember(Admin admin)
			throws UsernameExistException, PasswordStrengthLeakException, EmailExistException {
		MemberAuthService.super.checkPossibleToMakeMember(admin);

		// "admin" 이 회원가입 하기 위해서 추가로 체크해야 하는 작업들
	}

	@Override
	public Admin findById(Long id) throws IdNotExistException {
		return adminRepository.findById(id)
				.orElseThrow(() -> new IdNotExistException("User not found with id"));
	}

	@Override
	public Admin findByUsername(String username) throws UsernameNotExistException {
		return adminRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotExistException("User not found with username"));
	}

	@Override
	public Admin findByEmail(String email) throws EmailNotExistException {
		return adminRepository.findByEmail(email)
				.orElseThrow(() -> new EmailNotExistException("User not found with email"));
	}

	@Override
	public boolean isCorrectPassword(Member member, String rawPassword) {
		return passwordEncoder.matches(rawPassword, member.getPassword());
	}
}
