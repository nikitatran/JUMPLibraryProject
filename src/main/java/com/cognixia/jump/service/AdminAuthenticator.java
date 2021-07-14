package com.cognixia.jump.service;

import com.cognixia.jump.config.AppProperties;

public class AdminAuthenticator {
	
	private String adminPassword = AppProperties.get().getLibAdminPassword();
	
	public Admin get(String password) {
		return (password == adminPassword) ? new Admin() : null;
	}
	public Admin get(String username, String password) {
		return get(password);
	}

	public class Admin {
		public int getId() {
			return 1;
		}
	}
	
}
