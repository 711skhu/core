package com.shouwn.oj.model.entity.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.exception.InvalidParameterException;
import com.shouwn.oj.model.entity.BaseEntity;
import com.shouwn.oj.model.entity.member.Member;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = 0L;

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
	private List<Comment> children = new ArrayList<>();

	@Builder
	public Comment(String title, Member member, ProblemDetail problemDetail, Comment parent) {
		if (StringUtils.isBlank(title)) {
			throw new InvalidParameterException("댓글에는 항상 제목이 있어야 합니다.");
		}

		if (Objects.isNull(member)) {
			throw new InvalidParameterException("댓글 작성자가 없습니다.");
		}

		if (Objects.isNull(problemDetail)) {
			throw new InvalidParameterException("타겟 문제 상세가 없습니다.");
		}

		this.title = title;
		this.member = member;
		this.problemDetail = problemDetail;
		this.parent = parent;
	}

	@PreRemove
	public void assertNonChildren() {
		if (!children.isEmpty()) {
			throw new IllegalStateException("대댓글이 있어 삭제할 수 없습니다.");
		}
	}
}
