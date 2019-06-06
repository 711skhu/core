package com.shouwn.oj.model.entity.problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.shouwn.oj.model.entity.BaseEntity;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.member.Student;
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

	@Column(name = "active_date")
	private LocalDateTime activeDate;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Admin professor;

	@ManyToMany(mappedBy = "courses")
	private List<Student> students = new ArrayList<>();

	@OneToMany(mappedBy = "course")
	private List<Problem> problems = new ArrayList<>();

	@Builder
	public Course(String name, String description, Boolean enabled, Admin professor) {
		this.name = name;
		this.description = description;
		this.enabled = enabled;
		this.professor = professor;
	}

	public void activeCourse(Boolean enabled){
		this.enabled = enabled;

		if (enabled == true) {
			this.activeDate = LocalDateTime.now();
		} else if (enabled == false) {
			this.getStudents().clear();
		}
	}
}
