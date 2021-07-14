package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.models.PatronsModel;
import com.cognixia.jump.models.UserWithId;

public class PatronDao implements UserDao {

	private static final Connection conn = ConnectionManager.getConnection();
	
	private static final String PATRON_BY_ID = "SELECT * FROM patron WHERE patron_id = ?";
	private static final String PATRON_BY_CREDENTIALS = "SELECT * FROM patron WHERE username = ? AND password = ?";
	private static final String CREATE_PATRON = "INSERT INTO patron(first_name, last_name, username, password, account_frozen) VALUES(?, ?, ?, ?, ?)";
	private static final String UPDATE_PATRON = "UPDATE patron SET first_name = ?, last_name = ?, username = ?, password = ?, account_frozen = ? WHERE patron_id = ?";
	private static final String DELETE_PATRON = "DELETE FROM patron WHERE patron_id = ?";
	
	@Override
	public UserWithId getById(int patronId) {
		try (PreparedStatement pstmt = conn.prepareStatement(PATRON_BY_ID)) {
			pstmt.setInt(1, patronId);
			ResultSet rs = pstmt.executeQuery();
			return getPatronFromResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public PatronsModel getByCredentials(String username, String password) {
		try (PreparedStatement pstmt = conn.prepareStatement(PATRON_BY_CREDENTIALS)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			PatronsModel patron = getPatronFromResultSet(rs);
			return (patron != null && patron.getPassword().equals(password)) ? patron : null;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private PatronsModel getPatronFromResultSet(ResultSet rs) throws SQLException { // meant for rs of size 1
		PatronsModel patron = null;
		if (rs.next()) {
			int patronId = rs.getInt("patron_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String username = rs.getString("username");
			boolean frozen = rs.getBoolean("account_frozen");
			String password = rs.getString("password");
			patron = new PatronsModel(patronId, firstName, lastName, username, password, frozen);
		}
		return patron;
	}
	
	public boolean createPatron(PatronsModel patron) {
		return createPatron(patron, null);
	}
	public boolean createPatron(PatronsModel patron, HttpServletRequest request) {
		return createPatron(
			patron.getFirstName(), patron.getLastName(), patron.getUserName(),
				patron.getPassword(), patron.isFrozen(), request
		);
	}
	public boolean createPatron(String firstName, String lastName, String username, String password, boolean account_frozen) {
		return createPatron(firstName, lastName, username, password, account_frozen, null);
	}
	public boolean createPatron(String firstName, String lastName, String username, String password, boolean account_frozen, HttpServletRequest request) {
		try (PreparedStatement pstmt = conn.prepareStatement(CREATE_PATRON)) {
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, username);
			pstmt.setString(4, password);
			pstmt.setBoolean(5, account_frozen);
			if (pstmt.executeUpdate() > 0) return true;
		} catch (Exception e) {
			if (isDuplicateUsernameException(e) && request != null) {
				request.setAttribute("failMessage", getDuplicateUsernameFailMsg(username));
				System.out.println("exception: " + e.getMessage());
			}
			else e.printStackTrace();
		}
		return false;
	}
	
	public boolean updatePatron(PatronsModel patron) {
		return updatePatron(patron, null);
	}
	
	public boolean updatePatron(PatronsModel patron, HttpServletRequest request) {
		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_PATRON)) {
			pstmt.setString(1, patron.getFirstName());
			pstmt.setString(2, patron.getLastName());
			pstmt.setString(3,  patron.getUserName());
			pstmt.setString(4, patron.getPassword());
			pstmt.setBoolean(5, patron.isFrozen());
			pstmt.setInt(6, patron.getId());
			return (pstmt.executeUpdate() > 0);
		} catch (Exception e) {
			if (isDuplicateUsernameException(e) && request != null) {
				request.setAttribute("failMessage", getDuplicateUsernameFailMsg(patron.getUserName()));
				System.out.println("exception: " + e.getMessage());
			}
			else e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePatron(PatronsModel patron) {
		try (PreparedStatement pstmt = conn.prepareStatement(DELETE_PATRON)) {
			pstmt.setInt(1, patron.getId());
			return (pstmt.executeUpdate() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean isDuplicateUsernameException(Exception e) {
		String errMsg = e.getMessage();
		String lcErrMsg = errMsg == null ? null : errMsg.toLowerCase();
		if (errMsg != null && lcErrMsg.contains("duplicate") && lcErrMsg.contains("username")) {
			return true;
		}
		return false;
	}
	
	private String getDuplicateUsernameFailMsg(String username) {
		return (
			"That username is unavailable. There is already a user with the username \"" +
			username + "\"."
		);
	}
}
