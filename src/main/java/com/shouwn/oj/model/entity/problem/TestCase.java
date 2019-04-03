package com.shouwn.oj.model.entity.problem;

import javax.persistence.*;

import com.shouwn.oj.model.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
