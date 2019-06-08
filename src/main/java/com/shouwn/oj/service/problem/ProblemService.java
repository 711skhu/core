package com.shouwn.oj.service.problem;

import java.util.Optional;

import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.repository.problem.ProblemRepository;

import org.springframework.stereotype.Service;

@Service
public class ProblemService {

	private ProblemRepository problemRepository;

	public ProblemService(ProblemRepository problemRepository) {
		this.problemRepository = problemRepository;
	}

	public Optional<Problem> findById(Long problemId) {
		return problemRepository.findById(problemId);
	}
}
