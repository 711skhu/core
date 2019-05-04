package com.shouwn.oj.model.request.process;

import java.util.List;

import com.shouwn.oj.model.entity.problem.TestCase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRequest {
	private String sourceCode;
	private String language;
	private List<TestCase> testcases;
	private long pk;
}
