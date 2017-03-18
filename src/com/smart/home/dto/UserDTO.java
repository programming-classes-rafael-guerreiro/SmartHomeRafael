package com.smart.home.dto;

// DTO = Data Transfer Object
public class UserDTO {

	private final String name;

	public UserDTO(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
