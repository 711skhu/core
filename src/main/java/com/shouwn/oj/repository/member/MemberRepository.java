package com.shouwn.oj.repository.member;

import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;

public interface MemberRepository<T extends Member, ID> {

	Optional<T> findById(ID id);

	Optional<T> findByUsername(String username);
}
