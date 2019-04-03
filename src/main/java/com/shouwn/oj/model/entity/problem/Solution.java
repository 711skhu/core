package com.shouwn.oj.model.entity.problem;

import javax.persistence.*;

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
}
