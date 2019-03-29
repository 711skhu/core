package com.shouwn.oj.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "problem_detail")
public class ProblemDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private Integer sequence;

	@ManyToOne
	@JoinColumn(name = "problem_id")
	private Problem problem;

	@OneToMany(mappedBy = "problemDetail")
	private List<TestCase> testCases = new ArrayList<>();

	@OneToMany(mappedBy = "problemDetail")
	private List<Solution> solutions = new ArrayList<>();

	@Builder
	public ProblemDetail(String title, String content, Integer sequence, Problem problem) {
		this.title = title;
		this.content = content;
		this.sequence = sequence;
		this.problem = problem;
	}
}
