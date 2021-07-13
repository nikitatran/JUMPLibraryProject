package com.cognixia.jump.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
//	This class is a singleton.
	
	private static AppProperties instance = null;
	
	private String dbUrl = null;
	private String dbUsername = null;
	private String dbPassword = null;
	private String libAdminPassword = "admin123"; // initialized to default
	
	private AppProperties() {}
	private AppProperties(String dbUrl, String dbUsername, String dbPassword, String adminPassword) {
		this.dbUrl = dbUrl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		this.libAdminPassword = adminPassword;
	}
	
	public static AppProperties get() {
		if (instance == null) makeInstance();
		return instance;
	}
	
	public String getDbUrl() {
		return dbUrl;
	}
	public String getDbUsername() {
		return dbUsername;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public String getLibAdminPassword() {
		return libAdminPassword;
	}
	
	private static void makeInstance() {
		try {
			makeInstance("com/cognixia/jump/config/config.properties");
		} catch(Exception e) {
			new Exception("\nFAILED TO READ `config.properties`from `config` package.").printStackTrace();
			try {
				makeInstance("com/cognixia/jump/connection/config.properties");
			} catch (Exception e2) {
				new Exception("\nFAILED TO READ `config.properties`from `connection` package.").printStackTrace();
			}
		}
	}
	
	private static void makeInstance(String filePath) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(filePath);
		Properties props = new Properties();
		props.load(input);
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		String adminPassword = props.getProperty("adminpassword");
		instance = new AppProperties(url, username, password, adminPassword);
	}
	
}
