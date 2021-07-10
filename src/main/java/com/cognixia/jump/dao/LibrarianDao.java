package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.models.LibrarianModel;

public class LibrarianDao {

	private static final Connection conn = ConnectionManager.getConnection();
	
	private static final String GET_BY_ID = "SELECT * FROM librarian WHERE librarian_id = ?";
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
	
	private LibrarianModel getLibrarianFromResultSet(ResultSet rs) throws SQLException {
		if (rs.next()) {
			int librarianId = rs.getInt("librarian_id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			return new LibrarianModel(librarianId, username, password);
		}
		return null;
	}
	
}
