package com.shouwn.oj.model.entity;

import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "problem_detail_id")
	private ProblemDetail problemDetail;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@OneToMany(mappedBy = "parent")
	private List<Comment> children;

	@Builder
	public Comment(String title, Member member, ProblemDetail problemDetail, Comment parent) {
		this.title = title;
		this.member = member;
		this.problemDetail = problemDetail;
		this.parent = parent;
	}
}
