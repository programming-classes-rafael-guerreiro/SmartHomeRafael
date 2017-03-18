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

import com.smart.home.database.ConnectionHelper;
import com.smart.home.database.UserDAO;
import com.smart.home.database.UserDeviceDAO;
import com.smart.home.dto.UserDTO;
import com.smart.home.model.User;
import com.smart.home.model.UserDevice;

@WebServlet("/devices")
public class DevicesServlet extends HttpServlet {

	@Override // GET = leitura
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDevice[] allDevices = new UserDeviceDAO().list();
		
		req.setAttribute("data", allDevices);
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