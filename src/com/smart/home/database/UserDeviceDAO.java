package com.smart.home.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.smart.home.model.UserDevice;

public class UserDeviceDAO {

	// save // insert
	// update
	// delete
	// get 1 // select

	public List<UserDevice> list() {
		try (Connection connection = ConnectionPool.getConnection()) {
			List<UserDevice> data = new LinkedList<>();

			ResultSet result = connection.prepareStatement(
					"select u.user_id, u.name as user_name, d.device_id, d.name as device_name "
							+ "from users u, devices d where u.user_id = d.user_id").executeQuery();

			while (result.next()) {
				UserDevice userDevice = new UserDevice(result.getInt("user_id"), result.getString("user_name"),
						result.getInt("device_id"), result.getString("device_name"));

				data.add(userDevice);
			}

			return data;
		} catch (SQLException e) {
			throw new RuntimeException("Something went wrong with the database.", e);
		}
	}

}
