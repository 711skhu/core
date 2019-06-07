package com.shouwn.oj.repository.problem;

import java.util.List;

import com.shouwn.oj.model.entity.problem.TestCase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long>, TestCaseRepositoryCustom {
	List<TestCase> findByProblemDetailIdOrderById(long problemDetailId);
}

