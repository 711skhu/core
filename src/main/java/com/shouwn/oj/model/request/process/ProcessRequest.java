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
	private long problemDetailId;

	@Builder
	public ProcessRequest(String sourceCode, String language, long problemDetailId) {
		this.sourceCode = sourceCode;
		this.language = language;
		this.problemDetailId = problemDetailId;
	}
}
