package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.problem.Solution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long>, SolutionRepositoryCustom {
}
