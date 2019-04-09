package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.problem.ProblemDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemDetailRepository extends JpaRepository<ProblemDetail, Long>, ProblemDetailRepositoryCustom {
}
