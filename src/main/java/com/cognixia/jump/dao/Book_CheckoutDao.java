package com.cognixia.jump.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.models.BookModel;
import com.cognixia.jump.models.Book_CheckoutModel;
import com.cognixia.jump.models.CheckedOutBooksModel;

public class Book_CheckoutDao {
	
	private static final Connection connection = ConnectionManager.getConnection();
	
	private static final String INSERT_BOOK = "INSERT into book_checkout(patron_id, isbn, checkedout, due_date) values(?, ?, ?, ?)";		//Create book_checkout , book, patron
	private static final String	UPDATE_BOOK = "UPDATE book_checkout SET isbn = ?, title = ?, desc = ?, rented = ?, added = ?";									//Update
	private static final String	BOOK_BY_ID = "SELECT * FROM book_checkout WHERE id = ?";								//SelectID
	
	private static final String GET_CURRCHECKEDOUT = "select * from book_checkout left join book on book_checkout.isbn = book.isbn where patron_id = ? AND returned IS NULL";
	private static final String GET_PREVCHECKEDOUT = "select * from book_checkout left join book on book_checkout.isbn = book.isbn where patron_id = ? AND returned IS NOT NULL";
	
	
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
	
	public List<CheckedOutBooksModel> getPrevCheckedOutBooks(int patronId){
		
		List<CheckedOutBooksModel> prevCheckedOut = new ArrayList<CheckedOutBooksModel>();
		
		try(PreparedStatement pstmt = connection.prepareStatement(GET_PREVCHECKEDOUT);){
			pstmt.setInt(1, patronId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				prevCheckedOut.add(new CheckedOutBooksModel(
						rs.getString("isbn"), 
						rs.getDate("checkedout"), 
						rs.getDate("due_date"), 
						rs.getDate("returned"), 
						rs.getString("title"), 
						rs.getString("description"))
						);
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prevCheckedOut;
	}
	
	public List<CheckedOutBooksModel> getCurrCheckedOutBooks(int patronId){
		
		List<CheckedOutBooksModel> currCheckedOut = new ArrayList<CheckedOutBooksModel>();
		
		try(PreparedStatement pstmt = connection.prepareStatement(GET_CURRCHECKEDOUT);){
			pstmt.setInt(1, patronId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				currCheckedOut.add(new CheckedOutBooksModel(
						rs.getString("isbn"), 
						rs.getDate("checkedout"), 
						rs.getDate("due_date"), 
						rs.getString("title"), 
						rs.getString("descr"))
						);
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currCheckedOut;
	}
	
	
}
