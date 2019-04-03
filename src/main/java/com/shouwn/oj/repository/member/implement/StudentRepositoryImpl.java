package com.shouwn.oj.repository.member.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.member.StudentRepositoryCustom;

public class StudentRepositoryImpl implements StudentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public StudentRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
