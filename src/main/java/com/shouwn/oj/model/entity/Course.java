package com.shouwn.oj.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "course")
public class Course extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Boolean enabled;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Admin professor;

	@ManyToMany(mappedBy = "course")
	private List<Member> students = new ArrayList<>();

	@OneToMany(mappedBy = "course")
	private List<Problem> problems = new ArrayList<>();

	@Builder
	public Course(String name, String description, Boolean enabled, Admin professor) {
		this.name = name;
		this.description = description;
		this.enabled = enabled;
		this.professor = professor;
	}
}
