package com.shouwn.oj.model.entity.problem;

import javax.persistence.*;

import com.querydsl.core.annotations.QueryInit;
import com.shouwn.oj.model.entity.BaseEntity;
import com.shouwn.oj.model.entity.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "solution")
public class Solution extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Integer score;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@QueryInit("problem.*")
	@ManyToOne
	@JoinColumn(name = "problem_detail_id")
	private ProblemDetail problemDetail;

	@Builder
	public Solution(String content, Integer score, Member member, ProblemDetail problemDetail) {
		this.content = content;
		this.score = score;
		this.member = member;
		this.problemDetail = problemDetail;
	}

	/** 더 높은 점수를 가진 Solution 을 리턴하는 메소드 */
	public static Solution getBiggerScoreSolution(Solution s1 , Solution s2) {
		if (s1.getScore() > s2.getScore()) {
			return s1;
		} else {
			return s2;
		}
	}

	public boolean isPerfectScore() {
		return getProblemDetail().isPerfectScore(this);
	}
}
