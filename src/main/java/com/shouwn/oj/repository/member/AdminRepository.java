package com.shouwn.oj.repository.member;

import com.shouwn.oj.model.entity.member.Admin;

public interface AdminRepository extends StudentRepositoryCustom, MemberRepository<Admin, Long> {
}
