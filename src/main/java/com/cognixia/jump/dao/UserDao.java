package com.cognixia.jump.dao;

import com.cognixia.jump.models.UserWithId;

/*
 * This interface created so methods can be called
 * 		from a generic variable representing a DAO for
 * 		any of the user types (patron, librarian).
 */
public interface UserDao {
	
	public UserWithId getById(int userId);
	
	public UserWithId getByCredentials(String username, String password);
	
}
