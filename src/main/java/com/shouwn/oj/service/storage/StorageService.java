package com.shouwn.oj.service.storage;

import java.nio.file.Path;

import com.shouwn.oj.exception.storage.StorageException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	/**
	 * MultipartFile 을 저장하는 메소드
	 *
	 * @param file 저장하고자 하는 파일
	 * @return 저장된 path
	 */
	Path store(MultipartFile file) throws StorageException;

	/**
	 * 파일명으로 파일 경로를 찾는 메소드
	 *
	 * @param filename 파일 이름
	 * @return 파일 경로
	 */
	Path getPath(String filename) throws StorageException;

	/**
	 * 파일명으로 파일을 찾는 메소드
	 *
	 * @param filename 찾고자 하는 파일 이름
	 * @return 리소스
	 */
	Resource load(String filename) throws StorageException;
}
