package com.shouwn.oj.repository.member;

import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository<T extends Member, ID> extends JpaRepository<T, ID> {

	Optional<T> findByUsername(String username);

	Optional<T> findByEmail(String email);
}
