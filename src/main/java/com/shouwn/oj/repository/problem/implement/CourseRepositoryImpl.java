package com.shouwn.oj.repository.problem.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.problem.CourseRepositoryCustom;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public CourseRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
