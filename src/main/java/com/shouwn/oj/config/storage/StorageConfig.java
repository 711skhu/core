package com.shouwn.oj.config.storage;

import com.shouwn.oj.service.storage.*;
import org.apache.tika.Tika;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

	@Bean
	public StorageService storageService(FileSystemStorageService storageService) {
		return new StorageServiceSecurityProxy(storageService);
	}

	@Bean
	public ImageStorageService imageStorageService(Tika tika, FileSystemStorageService storageService) {
		return new StorageServiceSecurityProxy(new ImageStorageServiceImpl(storageService, tika));
	}

	@Bean
	public FileSystemStorageService fileSystemStorageService(StorageProperties properties) {
		return new FileSystemStorageService(properties);
	}

	@Bean
	public Tika tika() {
		return new Tika();
	}

	@ConditionalOnMissingBean
	@Bean
	public StorageProperties storageProperties() {
		return new DefaultStorageProperties();
	}
}
