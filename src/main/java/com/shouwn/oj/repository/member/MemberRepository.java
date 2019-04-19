package com.shouwn.oj.repository.member;

import com.shouwn.oj.model.entity.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
