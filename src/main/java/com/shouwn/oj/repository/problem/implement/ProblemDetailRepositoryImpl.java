package com.shouwn.oj.repository.problem.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.problem.ProblemDetailRepositoryCustom;

public class ProblemDetailRepositoryImpl implements ProblemDetailRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public ProblemDetailRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
