package com.shouwn.oj.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.annotation.PostConstruct;

import com.shouwn.oj.config.storage.StorageProperties;
import com.shouwn.oj.exception.storage.StorageException;
import com.shouwn.oj.exception.storage.StorageFileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(this.rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	/**
	 * MultipartFile 을 저장하는 메소드
	 *
	 * @param file 저장하고자 하는 파일
	 * @return 저장된 path
	 */
	@Override
	public Path store(MultipartFile file) throws StorageException {
		String filename = getDateFilename(file.getOriginalFilename());

		try {
			try (InputStream inputStream = file.getInputStream()) {
				Path path = this.rootLocation.resolve(filename);
				Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

				return path;
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	/**
	 * 파일명으로 파일 경로를 찾는 메소드
	 *
	 * @param filename 파일 이름
	 * @return 파일 경로
	 */
	@Override
	public Path getPath(String filename) {
		return rootLocation.resolve(filename);
	}

	/**
	 * 파일명으로 파일을 찾는 메소드
	 *
	 * @param filename 찾고자 하는 파일 이름
	 * @return 리소스
	 */
	@Override
	public Resource load(String filename) {
		try {
			Path file = getPath(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	private String getDateFilename(String filename) {
		StringUtils.cleanPath(filename);

		String[] dirs = filename.split("/");
		dirs[dirs.length - 1] = new Date().toString() + dirs[dirs.length - 1];

		return String.join("/", dirs);
	}
}
