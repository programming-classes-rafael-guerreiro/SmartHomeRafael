package com.smart.home.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ConnectionPool {

	private static final int MAX = 5;

	private static final Queue<Connection> QUEUE = new LinkedList<>();

	private static final ExecutorService EXECUTOR;
	static {
		EXECUTOR = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

	private ConnectionPool() {
	}

	private static void createConnections() {
		EXECUTOR.execute(new ConnectionCreator());
	}

	public static Connection getConnection() {
		if (QUEUE.isEmpty())
			QUEUE.add(connect());

		Connection poll = QUEUE.poll();
		createConnections();
		return poll;
	}

	private static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to locate com.mysql.jdbc.Driver", e);
		}

		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_home", "root", "");

			System.out.println("## Got a new Connection!!");

			return connection;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to connect to database.", e);
		}
	}

	private static final class ConnectionCreator implements Runnable {
		@Override
		public void run() {
			while (QUEUE.size() < MAX) {
				QUEUE.add(connect());
			}
		}
	}
}
