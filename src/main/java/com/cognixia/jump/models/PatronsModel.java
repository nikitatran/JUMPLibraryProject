package com.cognixia.jump.models;

public class PatronsModel implements UserWithId {
	private int patronId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private boolean frozen;
	
	public PatronsModel(int patronId, String firstName, String lastName, String username, String password, boolean frozen) {
		super();
		this.patronId = patronId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = username;
		this.password = password;
		this.frozen = frozen;
	}
	public PatronsModel(String firstName, String lastName, String userName, String password, boolean frozen) {
		this(-1, firstName, lastName, userName, password, frozen);
	}

	@Override
	public int getId() {
		return patronId;
	}
	public void setId(int patronId) {
		this.patronId = patronId;
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
	
	public boolean isValidInfo() {
		String[] attrs = { firstName, lastName, userName, password };
		for (String attrVal : attrs) {
			if (attrVal == null || attrVal.equals("")) {
				return false;
			}
		}
		return true;
	}
	

	@Override
	public String toString() {
		return "PatronsModel [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", password=" + password + ", frozen=" + frozen + "]";
	}
	
	
	
	
}
