package com.shouwn.oj.service.storage;

import java.nio.file.Path;

import com.shouwn.oj.exception.storage.StorageException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class StorageServiceSecurityProxy implements ImageStorageService, StorageService {

	private final StorageService storageService;

	public StorageServiceSecurityProxy(StorageService storageService) {
		this.storageService = storageService;
	}

	@Override
	public Path store(MultipartFile file) throws StorageException {
		if (file == null || file.getOriginalFilename() == null) {
			throw new StorageException("file or filename is null");
		}

		assertFilenameNotRelative(file.getOriginalFilename());

		if (file.isEmpty()) {
			throw new StorageException("Failed to store empty file");
		}

		return storageService.store(file);
	}

	@Override
	public Path getPath(String filename) throws StorageException {
		assertFilenameNotRelative(filename);
		return storageService.getPath(filename);
	}

	@Override
	public Resource load(String filename) throws StorageException {
		return null;
	}

	/**
	 * 보안상의 이유로 파일명을 체크하는 메소드
	 *
	 * @param filename 파일명
	 */
	private void assertFilenameNotRelative(String filename) throws StorageException {
		if (filename.contains("..")) {
			// This is a security check
			throw new StorageException("filename should not contain relative path. filename: " + filename);
		}
	}
}
