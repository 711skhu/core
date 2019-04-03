package com.shouwn.oj.repository.problem.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.problem.TestCaseRepositoryCustom;

public class TestCaseRepositoryImpl implements TestCaseRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TestCaseRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
