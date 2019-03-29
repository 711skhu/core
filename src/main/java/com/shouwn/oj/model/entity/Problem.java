package com.shouwn.oj.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.shouwn.oj.model.enums.ProblemType;
import lombok.*;

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

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@OneToMany(mappedBy = "problem")
	private List<ProblemDetail> problemDetails = new ArrayList<>();

	@Builder
	public Problem(ProblemType type, String title, LocalDateTime startDate, LocalDateTime endDate, Course course) {
		this.type = type;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.course = course;
	}
}
