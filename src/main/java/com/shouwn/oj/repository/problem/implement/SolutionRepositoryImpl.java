package com.shouwn.oj.repository.problem.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.problem.SolutionRepositoryCustom;

public class SolutionRepositoryImpl implements SolutionRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public SolutionRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
