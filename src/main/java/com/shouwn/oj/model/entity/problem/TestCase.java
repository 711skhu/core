package com.shouwn.oj.model.entity.problem;

import java.util.Objects;
import javax.persistence.*;

import com.shouwn.oj.exception.InvalidParameterException;
import com.shouwn.oj.model.entity.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "test_case")
public class TestCase extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String params;

	private String result;

	@ManyToOne
	@JoinColumn(name = "problem_detail_id")
	private ProblemDetail problemDetail;

	@Builder
	public TestCase(String params, String result, ProblemDetail problemDetail) {
		if (Objects.isNull(problemDetail)) {
			throw new InvalidParameterException("대상이 되는 문제가 있어야 합니다.");
		}

		if (StringUtils.isBlank(params) || StringUtils.isBlank(result)) {
			throw new InvalidParameterException("입력 혹은 출력 데이터가 없습니다.");
		}

		this.params = params;
		this.result = result;
		this.problemDetail = problemDetail;
	}
}
