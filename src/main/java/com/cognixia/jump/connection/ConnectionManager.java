package com.cognixia.jump.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private static Connection connection;
	
	//This is a Singleton class
	
	private static void makeConnection() {
		try {
			//access the info in our properties file
			Properties props = new Properties();
			
			props.load(new FileInputStream("resources/config.properties"));
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connected.");

		} catch (SQLException e) {
			System.out.println("Couldn't connect");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if(connection == null) {
			makeConnection();
		}
		return connection;
	}
	
//	public static void main(String[] args) {
//		Connection conn = ConnectionManager.getConnection();
//		
//		try {
//			conn.close();
//			System.out.println("Connection closed");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
