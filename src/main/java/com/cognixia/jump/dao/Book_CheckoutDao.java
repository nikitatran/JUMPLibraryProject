package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.models.Book_CheckoutModel;

public class Book_CheckoutDao {
	
	private static final Connection connection = ConnectionManager.getConnection();
	
	private static final String INSERT_BOOK = "INSERT into book_checkout(patron_id, isbn, checkedout, due_date) values(?, ?, ?, ?)";		//Create book_checkout , book, patron
	private static final String	UPDATE_BOOK = "UPDATE book_checkout SET isbn = ?, title = ?, desc = ?, rented = ?, added = ?";									//Update
	private static final String	BOOK_BY_ID = "SELECT * FROM book_checkout WHERE id = ?";									//SelectID
	
	public boolean addBook(Book_CheckoutModel checkout) {
		try(PreparedStatement pstmt = connection.prepareStatement(INSERT_BOOK)){
					pstmt.setInt(1, checkout.getPatronId());
					pstmt.setString(2, checkout.getIsbn());
					pstmt.setDate(3, checkout.getCheckedOutDate());
					pstmt.setDate(4, checkout.getDueDate());
				
					if (pstmt.executeUpdate() > 0) {
						return true;
					};
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return false;
	}
	
	public boolean updateCheckedOut(Book_CheckoutModel checkout) {
		try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_BOOK)) {

			pstmt.setInt(1, checkout.getPatronId());
			pstmt.setString(2, checkout.getIsbn());
			pstmt.setDate(3, checkout.getCheckedOutDate());
			pstmt.setDate(4, checkout.getDueDate());
			pstmt.setDate(5, checkout.getReturnedDate());
			
			// at least one row updated
			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Book_CheckoutModel myCheckout(int id) {
			
		Book_CheckoutModel checkout = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(BOOK_BY_ID)) {
			
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			// if product found, if statement run, if not null returned as product
			if(rs.next()) {
				int checkedOutId = rs.getInt("checkout_id");											
				int patronId = rs.getInt("patron_id");
				String isbn = rs.getString("isbn");
				Date outDate = rs.getDate("checkedout");
				Date dueDate = rs.getDate("due_date");
				Date returnDate = rs.getDate("returned");
				
				checkout = new Book_CheckoutModel(checkedOutId, patronId, isbn, outDate, dueDate, returnDate);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return checkout;
	}
}
