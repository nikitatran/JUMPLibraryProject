package com.cognixia.jump.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cognixia.jump.config.AppProperties;

public class ConnectionManager {
	private static Connection connection;
	
	//This is a Singleton class
	
	private static void makeConnection() {
		try {
			//access the info in our properties file
			AppProperties props = AppProperties.get();
			
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(
					props.getDbUrl(), props.getDbUsername(), props.getDbPassword());

			System.out.println("Connected.");
		} catch (SQLException e) {
			System.out.println("Couldn't connect");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if(connection == null) {
			makeConnection();
		}
		return connection;
	}

}
