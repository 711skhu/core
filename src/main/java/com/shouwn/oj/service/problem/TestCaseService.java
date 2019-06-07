package com.shouwn.oj.service.problem;

import java.util.List;

import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.repository.problem.TestCaseRepository;

import org.springframework.stereotype.Service;

@Service
public class TestCaseService {
	private final TestCaseRepository testCaseRepository;

	public TestCaseService(TestCaseRepository testCaseRepository) {
		this.testCaseRepository = testCaseRepository;
	}

	public List<TestCase> findByProblemDetailIdOrderById(long problemDetailId) {
		return testCaseRepository.findByProblemDetailIdOrderById(problemDetailId);
	}
}
