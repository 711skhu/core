package com.shouwn.oj.repository.problem;

import java.util.List;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.Solution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long>, SolutionRepositoryCustom {

	List<Solution> findSolutionsByProblemDetailAndMember(ProblemDetail problemDetail, Member member);
}
