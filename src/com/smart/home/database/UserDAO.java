package com.smart.home.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smart.home.dto.UserDTO;
import com.smart.home.model.User;

// DAO = Data Access Object
public class UserDAO {

	public List<User> list() {
		try (final Connection connection = ConnectionHelper.getConnection()) {
			final List<User> users = new ArrayList<>();

			final PreparedStatement statement = connection.prepareStatement("select user_id, name from users");
			final ResultSet result = statement.executeQuery();

			while (result.next()) {
				final int id = result.getInt("user_id");
				final String name = result.getString("name");

				users.add(new User(id, name));
			}

			return users;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to list user.", e);
		}
	}

	public User get(final int id) {
		try (final Connection connection = ConnectionHelper.getConnection()) {
			final PreparedStatement statement = connection.prepareStatement(
					"select user_id, name from users where user_id = ?");
			statement.setInt(1, id);

			final ResultSet result = statement.executeQuery();

			if (result.next()) {
				final int returnedId = result.getInt("user_id");
				final String name = result.getString("name");

				return new User(returnedId, name);
			}

			return null;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to get user with id " + id + ".", e);
		}
	}

	public User create(final UserDTO dto) {
		try (final Connection connection = ConnectionHelper.getConnection()) {
			connection.setAutoCommit(false);

			final PreparedStatement statement = connection.prepareStatement("insert into users (name) values (?)");
			statement.setString(1, dto.getName());

			final boolean didItWork = statement.execute();

			if (didItWork) {
				final ResultSet keys = statement.getGeneratedKeys();
				int id = keys.getInt(1);

				connection.commit();

				return get(id);
			}

			connection.rollback();
			throw new IllegalArgumentException("Unable to create user because dto is invalid.");
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create user.", e);
		}
	}

	public User update(final int id, final UserDTO dto) {
		try (final Connection connection = ConnectionHelper.getConnection()) {
			connection.setAutoCommit(false);

			final PreparedStatement statement = connection.prepareStatement(
					"update users set name = ? where user_id = ?");
			statement.setString(1, dto.getName());
			statement.setInt(2, id);

			int rows = statement.executeUpdate();

			if (rows == 1) {
				connection.commit();
				return get(id);
			}

			connection.rollback();
			throw new IllegalArgumentException("Unable to update user because dto is invalid.");
		} catch (SQLException e) {
			throw new RuntimeException("Unable to update user.", e);
		}
	}
}
