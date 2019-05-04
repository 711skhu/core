package com.shouwn.oj.model.request.process;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRequest {
	private String sourceCode;
	private String language;
	private List<String> testcase;
	private long pk;
}
