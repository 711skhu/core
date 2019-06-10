package com.shouwn.oj.service.problem;

import java.util.List;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.Solution;
import com.shouwn.oj.repository.problem.SolutionRepository;

import org.springframework.stereotype.Service;

@Service
public class SolutionService {

	private SolutionRepository solutionRepository;

	public SolutionService(SolutionRepository solutionRepository) {
		this.solutionRepository = solutionRepository;
	}

	public List<Solution> findSolutionsByProblemDetailAndMember(ProblemDetail problemDetail, Member member) {
		return solutionRepository.findSolutionsByProblemDetailAndMember(problemDetail, member);
	}
}
