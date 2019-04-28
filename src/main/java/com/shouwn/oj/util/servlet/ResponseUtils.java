package com.shouwn.oj.util.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

public class ResponseUtils {

	/**
	 * response 의 request body 에 json 을 set 하는 메소드
	 *
	 * @param response response 객체
	 * @param json     request body 에 적을 json
	 */
	public static void setJsonToResponse(HttpServletResponse response, String json) throws IOException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}
