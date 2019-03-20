package com.shouwn.oj.repository;

import java.util.List;

import com.shouwn.oj.model.entity.Member;

public interface MemberRepositoryCustom {

	List<Member> searchLike(Member member);
}
