package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.problem.Problem;

import com.shouwn.oj.model.enums.ProblemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemRepositoryCustom {

    List<Problem> findByCourseIdAndByType(Long courseId,ProblemType problemType);

}
