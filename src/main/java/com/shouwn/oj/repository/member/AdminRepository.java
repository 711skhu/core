package com.shouwn.oj.repository.member;

import com.shouwn.oj.model.entity.member.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long>, StudentRepositoryCustom {
}
