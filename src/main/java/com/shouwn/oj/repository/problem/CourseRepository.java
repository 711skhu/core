package com.shouwn.oj.repository.problem;

import java.util.List;

import com.shouwn.oj.model.entity.problem.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

	List<Course> findByProfessorId(Long adminId);
}
