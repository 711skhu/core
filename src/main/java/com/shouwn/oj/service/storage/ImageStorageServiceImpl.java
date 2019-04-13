package com.shouwn.oj.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import com.shouwn.oj.exception.storage.StorageException;
import com.shouwn.oj.exception.storage.StorageFormatException;
import org.apache.tika.Tika;

import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageStorageServiceImpl implements ImageStorageService {

	private static final String PREFIX = "image/";

	private final StorageService storageService;
	private final Tika tika;

	public ImageStorageServiceImpl(StorageService storageService, Tika tika) {
		this.storageService = storageService;
		this.tika = tika;
	}

	@Override
	public Path store(MultipartFile file) throws StorageException {
		try {
			InputStream inputStream = file.getInputStream();
			String mimeType = tika.detect(inputStream);

			if (!mimeType.startsWith("image/")) {
				throw new StorageFormatException(mimeType + "은 이미지 파일이 아닙니다.");
			}

			MultipartFile imageFile = new MockMultipartFile(
					file.getName(),
					PREFIX + file.getOriginalFilename(),
					mimeType,
					inputStream
			);

			return storageService.store(imageFile);
		} catch (IOException e) {
			throw new StorageException("Failed to store file", e);
		}
	}

	@Override
	public Path getPath(String filename) {
		return storageService.getPath(PREFIX + filename);
	}

	@Override
	public Resource load(String filename) {
		return storageService.load(PREFIX + filename);
	}
}
