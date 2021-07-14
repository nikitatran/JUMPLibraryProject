package com.cognixia.jump.service;

import com.cognixia.jump.config.AppProperties;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.models.UserWithId;

	public class AdminMockDao implements UserDao {

		private static final String PASSWORD = AppProperties.get().getLibAdminPassword();
		
		@Override
		public UserWithId getById(int id) {
			return (id == -1 || id == 0) ? null : new Admin();
		}
		
		@Override
		public UserWithId getByCredentials(String username, String password) {
			return (password.equals(PASSWORD)) ? new Admin() : null;
		}
		
		public UserWithId getByCredentials(String password) {
			return getByCredentials(null, password);
		}
		
		public class Admin implements UserWithId {
			@Override
			public int getId() {
				return 1;
			}
		}
		
	}
