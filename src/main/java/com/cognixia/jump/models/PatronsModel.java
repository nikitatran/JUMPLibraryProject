package com.cognixia.jump.models;

public class PatronsModel {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private boolean frozen;
	
	public PatronsModel(String firstName, String lastName, String userName, String password, boolean frozen) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.frozen = frozen;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isFrozen() {
		return frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	@Override
	public String toString() {
		return "PatronsModel [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", frozen=" + frozen + "]";
	}
	
	
	
	
}