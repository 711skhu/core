package com.shouwn.oj.repository.member.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.member.AdminRepositoryCustom;

public class AdminRepositoryImpl implements AdminRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public AdminRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
