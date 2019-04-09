package com.shouwn.oj.repository.problem.implement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.repository.problem.CommentRepositoryCustom;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

}
