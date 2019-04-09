package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.problem.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemRepositoryCustom {
}
