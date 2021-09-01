package com.felix.EmployeeDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	private static String dbhost = "jdbc:mysql://<db-endpoint>.us-east-1.rds.amazonaws.com:3306/<db-table>";
	private static String username = "<user>";
	private static String password = "<password>";
	private static Connection conn;

	@SuppressWarnings("finally")
	public static Connection createNewDbConnection() {
		try {
			conn = DriverManager.getConnection(dbhost, username, password);
		} catch(SQLException e) {
			System.out.println("Cannot create database connection");
			e.printStackTrace();
		} finally {
			return conn;
		}
	}
}
