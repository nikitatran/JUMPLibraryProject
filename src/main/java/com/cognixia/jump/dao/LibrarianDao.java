package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.models.LibrarianModel;

public class LibrarianDao {

	private static final Connection conn = ConnectionManager.getConnection();
	
	private static final String GET_BY_ID = "SELECT * FROM librarian WHERE librarian_id = ?";
	private static final String GET_JUST_CREATED_ID = "SELECT LAST_INSERT_ID()";
	private static final String GET_BY_CREDENTIALS = "SELECT * FROM librarian WHERE username = ? AND password = ?";
	private static final String CREATE_LIBRARIAN = "NSERT INTO librarian(username, password) VALUES(?, ?)";
	private static final String UPDATE_LIBRARIAN = "UPDATE librarian SET username = ?, password = ? WHERE librarian_id = ?";
	private static final String DELETE_LIBRARIAN = "DELETE FROM librarian WHERE librarian_id = ?";
	
	public LibrarianModel getById(int id) {
		try (PreparedStatement pstmt = conn.prepareStatement(GET_BY_ID)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			return getLibrarianFromResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LibrarianModel getByCredentials(String username, String password) {
		try (PreparedStatement pstmt = conn.prepareStatement(GET_BY_CREDENTIALS)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			LibrarianModel librarian = getLibrarianFromResultSet(rs);
			if (librarian == null || librarian.getPassword() != password) {
				return null;
			}
			return librarian;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LibrarianModel createLibrarian(LibrarianModel librarian) {
		return createLibrarian(librarian, null);
	}
	public LibrarianModel createLibrarian(LibrarianModel librarian, HttpServletRequest request) {
		return createLibrarian(librarian.getUsername(), librarian.getPassword(), request);
	}
	public LibrarianModel createLibrarian(String username, String password) {
		return createLibrarian(username, password, null);
	}
	public LibrarianModel createLibrarian(String username, String password, HttpServletRequest request) {
		try (PreparedStatement pstmt = conn.prepareStatement(CREATE_LIBRARIAN)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			if (pstmt.executeUpdate() > 0) {
				int id = getLastId();
				return new LibrarianModel(id, username, password);
			}
		} catch (Exception e) {
			if (isDuplicateUsernameException(e) && request != null) { // SAME AS PATRON DAO
				request.setAttribute("failMessage", getDuplicateUsernameFailMsg(username));
				System.out.println("exception: " + e.getMessage());
			}
			else e.printStackTrace();
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateLibrarian(LibrarianModel librarian) {
		return updateLibrarian(librarian, null);
	}
	public boolean updateLibrarian(LibrarianModel librarian, HttpServletRequest request) {
		try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_LIBRARIAN)) {
			pstmt.setString(1, librarian.getUsername());
			pstmt.setString(2, librarian.getPassword());
			pstmt.setInt(3, librarian.getId());
			return (pstmt.executeUpdate() > 0);
		} catch(Exception e) {
			// SAME AS PATRON DAO
			if (isDuplicateUsernameException(e) && request != null) {
				String dupUsernameMsg = getDuplicateUsernameFailMsg(librarian.getUsername());
				request.setAttribute("failMessage", dupUsernameMsg);
				System.out.println("exception: " + e.getMessage());
			}
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteLibrarian(LibrarianModel librarian) {
		return deleteLibrarian(librarian.getId());
	}
	public boolean deleteLibrarian(int librarianId) {
		try (PreparedStatement pstmt = conn.prepareStatement(DELETE_LIBRARIAN)) {
			pstmt.setInt(1, librarianId);
			return (pstmt.executeUpdate() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private int getLastId() throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(GET_JUST_CREATED_ID);
		return pstmt.executeQuery().getInt(1);
	}
	
	private LibrarianModel getLibrarianFromResultSet(ResultSet rs) throws SQLException {
		if (rs.next()) {
			int librarianId = rs.getInt("librarian_id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			return new LibrarianModel(librarianId, username, password);
		}
		return null;
	}
	
	// SAME AS IN PATRON DAO.
		// (should probably be put in 1 place to be referenced by both)
	private boolean isDuplicateUsernameException(Exception e) {
		String errMsg = e.getMessage();
		if (errMsg == null) return false;
		String lcErrMsg = errMsg.toLowerCase();
		return (lcErrMsg.contains("duplicate") && lcErrMsg.contains("username"));
	}

	// SAME AS PATRON DAO
	private String getDuplicateUsernameFailMsg(String username) {
		return (
			"That username is unavailable. There is already a librarian account with the username \"" +
			username + "\"."
		);
	}
	
	
}
