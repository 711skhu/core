package com.shouwn.oj.model.entity.problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.*;

import com.shouwn.oj.exception.InvalidParameterException;
import com.shouwn.oj.model.entity.BaseEntity;
import com.shouwn.oj.model.entity.member.Admin;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "course")
public class Course extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = 0L;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Boolean enabled;

	@Column(name = "active_date")
	private LocalDateTime activeDate;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Admin professor;

	@OneToMany(mappedBy = "course")
	private List<CourseRegister> registers = new ArrayList<>();

	@OneToMany(mappedBy = "course")
	private List<Problem> problems = new ArrayList<>();

	@Builder
	public Course(String name, String description, Admin professor) {
		if (StringUtils.isBlank(name)) {
			throw new InvalidParameterException("강좌 이름이 없습니다.");
		}

		if (Objects.isNull(professor)) {
			throw new InvalidParameterException("강좌 생성자가 없습니다.");
		}

		this.name = name;
		this.description = Optional.ofNullable(description).orElse("");
		this.professor = professor;
		this.enabled = false;
		this.activeDate = LocalDateTime.MIN;
	}
}
