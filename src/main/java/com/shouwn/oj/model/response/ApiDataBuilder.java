package com.shouwn.oj.model.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ApiDataBuilder {

	private Map<String, Object> data = new HashMap<>();

	public ApiDataBuilder addData(String name, Object data) {
		this.data.put(name, data);
		return this;
	}

	public Map<String, Object> packaging() {
		return this.data;
	}
}
