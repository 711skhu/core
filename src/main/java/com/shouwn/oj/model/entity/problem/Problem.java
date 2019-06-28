package com.shouwn.oj.model.entity.problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.*;

import com.shouwn.oj.exception.InvalidParameterException;
import com.shouwn.oj.model.entity.BaseEntity;
import com.shouwn.oj.model.enums.ProblemType;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "problem")
public class Problem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ProblemType type;

	@Column(nullable = false)
	private String title;

	@Column
	private LocalDateTime startDate;

	@Column
	private LocalDateTime endDate;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@OneToMany(mappedBy = "problem")
	private List<ProblemDetail> problemDetails = new ArrayList<>();

	@Builder
	public Problem(ProblemType type, String title, LocalDateTime startDate, LocalDateTime endDate, Course course) {
		if (Objects.isNull(type)) {
			throw new InvalidParameterException("문제 타입이 없습니다.");
		}

		if (StringUtils.isBlank(title)) {
			throw new InvalidParameterException("문제 제목이 없습니다.");
		}

		if (Objects.isNull(course)) {
			throw new InvalidParameterException("문제의 소속 강좌가 없습니다.");
		}

		this.type = type;
		this.title = title;
		this.startDate = Optional.ofNullable(startDate).orElse(LocalDateTime.of(2000, 1, 1, 0, 0));
		this.endDate = Optional.ofNullable(endDate).orElse(LocalDateTime.of(2000, 1, 1, 0, 0));
		this.course = course;
	}

	public int fullScore() {
		return problemDetails.stream()
				.map(ProblemDetail::fullScore)
				.reduce(0, Integer::sum);
	}
}
