package com.shouwn.oj.service.problem;

import java.util.List;
import java.util.Optional;

import com.shouwn.oj.model.entity.member.Member;
import com.shouwn.oj.model.entity.member.Student;
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

	public Optional<Solution> getMaxScoreSolution(List<Solution> solutions) {
		return solutions.stream().reduce(Solution::getBiggerScoreSolution);
	}

	public int getStudentScoreInProblemDetails(final Student student, List<ProblemDetail> problemDetails) {
		int sum = 0;

		for (ProblemDetail problemDetail : problemDetails) {
			List<Solution> studentSolutions = findSolutionsByProblemDetailAndMember(problemDetail, student);

			Optional<Solution> maxScoreSolution = getMaxScoreSolution(studentSolutions);

			sum += maxScoreSolution.isPresent() ? maxScoreSolution.get().getScore() : 0;
		}

		return sum;
	}
}
