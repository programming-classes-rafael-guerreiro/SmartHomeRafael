package com.smart.home.dto;

public class DeviceDTO {
	private final String name;
	private final int userId;

	public DeviceDTO(String name, int userId) {
		this.name = name;
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public int getUserId() {
		return userId;
	}
}
