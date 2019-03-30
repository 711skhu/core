package com.shouwn.oj.model.response;

/**
 * REST API 사용시 범용적으로 사용되는 응답 정의
 *
 * @param <T> 응답시 데이터 타입
 */
public interface ApiResponse<T> {

	/**
	 * REST API Status 코드
	 *
	 * @return Status 코드
	 */
	int getStatus();

	/**
	 * 프론트엔드 개발의 편의를 위한 서버측의 메세지
	 *
	 * @return 서버 측의 메세지
	 */
	String getMessage();

	/**
	 * API 응답 데이터
	 *
	 * @return 데이터
	 */
	T getData();
}
