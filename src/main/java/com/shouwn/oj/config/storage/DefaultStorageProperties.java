package com.shouwn.oj.config.storage;

public class DefaultStorageProperties implements StorageProperties {

	private String location = "/data/home/skhu/files/";

	@Override
	public String getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}
}
