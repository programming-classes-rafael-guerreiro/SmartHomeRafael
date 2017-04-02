package com.smart.home.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.smart.home.dto.DeviceDTO;
import com.smart.home.model.Device;

public class DeviceDAO {
	private final GenericDAO<Device> dao = new GenericDAO<Device>();
	private final GenericConverter<Device> converter = new GenericConverter<Device>() {
		@Override
		public Device convert(ResultSet result) throws SQLException {
			final int id = result.getInt("device_id");
			final String name = result.getString("name");
			final int userId = result.getInt("user_id");

			return new Device(id, name, userId);
		}
	};

	public List<Device> list() {
		return dao.list("select device_id, name, user_id from devices", converter);
	}

	public Device get(final int id) {
		return dao.get("select device_id, name, user_id from devices where device_id = ?", converter, id);
	}

	public Device create(final DeviceDTO dto) {
		int id = dao.create("insert into devices (name, user_id) values (?, ?)", dto.getName(), dto.getUserId());
		return get(id);
	}

	public Device update(final int id, final DeviceDTO dto) {
		dao.update("update devices set name = ?, user_id = ? where device_id = ?", id, dto.getName(), dto.getUserId());
		return get(id);
	}

}
