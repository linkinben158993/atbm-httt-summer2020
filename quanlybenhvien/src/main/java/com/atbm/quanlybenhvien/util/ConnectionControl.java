package com.atbm.quanlybenhvien.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionControl {

	public Connection createConnection(String userName, String password) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", userName, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
