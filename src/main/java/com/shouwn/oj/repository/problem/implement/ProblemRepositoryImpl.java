package com.shouwn.oj.repository.problem.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.problem.ProblemRepositoryCustom;

public class ProblemRepositoryImpl implements ProblemRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public ProblemRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
