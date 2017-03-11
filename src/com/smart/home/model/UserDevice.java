package com.smart.home.model;

public class UserDevice {
	private final int id;
	private final String userName;
	private final int deviceId;
	private final String deviceName;
	
	private final boolean even;

	public UserDevice(int userId, String userName, int deviceId, String deviceName) {
		this.id = userId;
		this.userName = userName;
		this.deviceId = deviceId;
		this.deviceName = deviceName;

		this.even = deviceId % 2 == 0;
	}

	public int getUserId() {
		return id;
	}

	public String getFormattedRow() {
		return new StringBuilder().append(id).append(" - ").append(getFullName()).toString();
	}

	public String getFullName() {
		return userName + " ~ " + deviceName;
	}

	public String getUserName() {
		return userName;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}
	
	public boolean isEven() {
		return even;
	}
}
