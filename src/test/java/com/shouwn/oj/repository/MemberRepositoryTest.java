package com.shouwn.oj.repository;

import java.util.List;

import com.shouwn.oj.model.entity.Member;
import com.shouwn.oj.model.enums.Role;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void checkQuerydsl() {
		Member member = Member.builder()
				.username("shon")
				.nickname("shon")
				.password("test123")
				.role(Role.STUDENT)
				.email("test@gmail.com")
				.build();

		memberRepository.save(member);

		//when
		List<Member> result = memberRepository.searchLike(member);

		Assertions.assertEquals(member, result.get(0));
	}
}
