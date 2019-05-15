package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.problem.Course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

    List<Course> findByProfessorId(Long adminId);
}
