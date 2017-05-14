package com.smart.home.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

// DAO = Data Access Object
public class GenericDAO<T> {

	// CRUD
	// Create
	// Read - get 1 / get N
	// Update
	// Delete

	public List<T> list(final String sql, GenericConverter<T> converter, Object... params) {
		try (final Connection connection = ConnectionPool.getConnection()) {
			final List<T> entities = new LinkedList<>();

			final PreparedStatement statement = connection.prepareStatement(sql);
			setParameters(statement, params);

			final ResultSet result = statement.executeQuery();

			while (result.next())
				entities.add(converter.convert(result));

			return entities;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to list.", e);
		}
	}

	public T get(final String sql, GenericConverter<T> converter, final int id) {
		List<T> list = list(sql, converter, id);

		if (list == null || list.isEmpty())
			return null;

		return list.get(0);
	}

	public int create(final String sql, Object... params) {
		try (final Connection connection = ConnectionPool.getConnection()) {
			connection.setAutoCommit(false);

			final PreparedStatement statement = connection.prepareStatement(sql);
			setParameters(statement, params);

			final boolean didItWork = statement.execute();

			if (didItWork) {
				final ResultSet keys = statement.getGeneratedKeys();
				int id = keys.getInt(1);

				connection.commit();
				return id;
			}

			connection.rollback();
			throw new IllegalArgumentException("Unable to create entity because parameters are invalid.");
		} catch (SQLException e) {
			throw new RuntimeException("Unable to create entity.", e);
		}
	}

	private void setParameters(final PreparedStatement statement, Object... params) throws SQLException {
		if (params == null)
			return;

		for (int index = 0; index < params.length; index++) {
			final Object param = params[index];
			final int parameterIndex = index + 1;

			if (param != null)
				statement.setObject(parameterIndex, param);
			else
				statement.setNull(parameterIndex, Types.NVARCHAR);
		}
	}

	public void update(final String sql, final int id, Object... params) {
		try (final Connection connection = ConnectionPool.getConnection()) {
			connection.setAutoCommit(false);

			final PreparedStatement statement = connection.prepareStatement(sql);
			setParameters(statement, params);

			statement.setInt(params == null ? 0 : params.length, id);

			int rows = statement.executeUpdate();

			if (rows == 1) {
				connection.commit();
				return;
			}

			connection.rollback();
			throw new IllegalArgumentException("Unable to update entity because dto is invalid.");
		} catch (SQLException e) {
			throw new RuntimeException("Unable to update.", e);
		}
	}
}
