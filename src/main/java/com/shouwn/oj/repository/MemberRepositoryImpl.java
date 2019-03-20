package com.shouwn.oj.repository;

import java.util.List;
import java.util.Objects;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shouwn.oj.model.entity.Member;
import com.shouwn.oj.model.entity.QMember;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<Member> searchLike(Member member) {
		QMember qMember = QMember.member;

		BooleanBuilder precondition = member.let(it -> {
			BooleanBuilder booleanBuilder = new BooleanBuilder();

			if (Objects.nonNull(it.getId())) {
				booleanBuilder.or(qMember.id.eq(it.getId()));
			}

			if (Objects.nonNull(it.getNickname())) {
				booleanBuilder.or(qMember.nickname.eq(it.getNickname()));
			}

			if (Objects.nonNull(it.getEmail())) {
				booleanBuilder.or(qMember.email.eq(it.getEmail()));
			}

			if (Objects.nonNull(it.getRole())) {
				booleanBuilder.or(qMember.role.eq(it.getRole()));
			}

			if (Objects.nonNull(it.getUsername())) {
				booleanBuilder.or(qMember.username.eq(it.getUsername()));
			}

			return booleanBuilder;
		});

		return queryFactory
				.selectFrom(qMember)
				.where()
				.fetch();
	}
}
