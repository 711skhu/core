package com.shouwn.oj.service.problem;

import javax.persistence.EntityNotFoundException;

import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.repository.problem.CourseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public Course findCourseById(Long id) {
		return courseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
