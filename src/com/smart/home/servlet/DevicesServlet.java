package com.smart.home.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.home.model.UserDevice;

@WebServlet("/devices")
public class DevicesServlet extends HttpServlet {

	@Override // GET = leitura
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long start = System.currentTimeMillis();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to locate mysql driver. " + e.getMessage());
			return;
		}

		UserDevice[] data = new UserDevice[2];

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_home", "root",
				"")) {
			start = System.currentTimeMillis();

			ResultSet result = connection.prepareStatement(
					"select u.user_id, u.name as user_name, d.device_id, d.name as device_name "
							+ "from users u, devices d where u.user_id = d.user_id").executeQuery();

			int line = 0;
			while (result.next()) {
				UserDevice userDevice = new UserDevice(result.getInt("user_id"), result.getString("user_name"),
						result.getInt("device_id"), result.getString("device_name"));

				data[line] = userDevice;

				line++;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Something went wrong with the database.", e);
		}
		// coloca na devices.jsp
		long end = System.currentTimeMillis();

		System.out.println("Took " + (end - start) + " ms.");

		req.setAttribute("data", data);
		req.getRequestDispatcher("WEB-INF/jsp/devices.jsp").forward(req, resp);
	}

	@Override // POST = criação
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override // PUT = alteração
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override // DELETE = deleção
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}