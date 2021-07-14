package com.cognixia.jump.models;

public class LibrarianModel implements UserWithId {
	
	private int id;
	private String username;
	private String password;
	
	public LibrarianModel(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public LibrarianModel(String username, String password) {
		this(-1, username, password);
	}
	
	@Override
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isValidInfo() {
		String[] attrs = { username, password };
		for (String attrVal : attrs) {
			if (attrVal == null || attrVal.equals("")) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "LibrarianModel [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
}
