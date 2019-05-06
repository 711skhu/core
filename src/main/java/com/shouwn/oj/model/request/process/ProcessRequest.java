package com.shouwn.oj.model.request.process;

import java.util.List;

import com.shouwn.oj.model.entity.problem.TestCase;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRequest {

	private String sourceCode;
	private String language;
	private List<TestCase> testCases;
	private long pk;

	@Builder
	public ProcessRequest(String sourceCode, String language, List<TestCase> testCases, long pk) {
		this.sourceCode = sourceCode;
		this.language = language;
		this.testCases = testCases;
		this.pk = pk;
	}
}
