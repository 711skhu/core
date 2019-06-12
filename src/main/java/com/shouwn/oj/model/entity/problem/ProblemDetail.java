package com.shouwn.oj.model.entity.problem;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.shouwn.oj.model.entity.BaseEntity;
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

	/** 테스트 케이스를 모두 통과했을 경우(=만점) 점수를 리턴하는 메소드 */
	public int fullScore() {
		return getTestCases().size();
	}

	/** 매개변수로 받아온 Solution 이 만점인지 확인하는 메소드 */
	public boolean isPerfectScore(Solution solution) {
		int fullScore = this.fullScore();

		if (fullScore == 0) {
			throw new IllegalStateException("문제에 테스트 케이스가 없는데 정답을 제출할 수는 없습니다.");
		}

		return solution.getScore() == fullScore();
	}
}
