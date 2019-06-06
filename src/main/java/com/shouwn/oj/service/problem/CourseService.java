package com.shouwn.oj.service.problem;

import java.util.List;

import java.util.Optional;

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

	public void saveCourse(Course course) {
		courseRepository.save(course);
	}

	public Optional<Course> findCourseById(Long courseId) {
		return courseRepository.findById(courseId);
	}

	public List<Course> findCoursesByEnabled() {
		return courseRepository.findCoursesByEnabled(true);
	}
}
