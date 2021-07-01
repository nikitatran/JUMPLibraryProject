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

public class BookDao {

	private static final Connection connection = ConnectionManager.getConnection();
	
	private static final String ALL_BOOKS = "SELECT * FROM book";
	private static final String BOOK_BY_ISBN = "SELECT * FROM book WHERE isbn = ?";
	private static final String INSERT_BOOK = "INSERT INTO book(isbn, title, descr, rented, added_to_library) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_BOOK = "UPDATE book SET isbn = ?, title = ?, descr = ?, rented = ?, added_to_library = ? WHERE isbn = ?";
	private static final String DELETE_BOOK = "DELETE FROM book WHERE isbn = ?";
	
	public List<BookModel> getAllBooks() {
		
		List<BookModel> products = new ArrayList<BookModel>();
		
		try(PreparedStatement pstmt = connection.prepareStatement(ALL_BOOKS);
				ResultSet rs = pstmt.executeQuery();){
			
			while (rs.next()) {
				products.add(new BookModel(rs.getInt("isbn"), 
											rs.getString("title"), 
											rs.getString("descr"), 
											rs.getBoolean("rented"),
											rs.getDate("added_to_library")
						));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
	
	public BookModel getBookByIsbn(int isbn) {
		BookModel book = null;
		
		try(PreparedStatement pstmt = connection.prepareStatement(BOOK_BY_ISBN)) {
			
			pstmt.setInt(1, isbn);
			
			ResultSet rs = pstmt.executeQuery();
			
			// if product found, if statement run, if not null returned as product
			if(rs.next()) {
				int bookIsbn = rs.getInt("isbn");
				String title = rs.getString("title");
				String desc = rs.getString("descr");
				boolean rented = rs.getBoolean("rented");
				Date added = rs.getDate("added_to_library");
				
				book = new BookModel(bookIsbn, title, desc, rented, added);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	public boolean addBook(BookModel book) {
		try(PreparedStatement pstmt = connection.prepareStatement(INSERT_BOOK)){
					//INSERT INTO book(isbn, title, descr, rented, added_to_library) VALUES (?, ?, ?, ?, ?)
					pstmt.setInt(1, book.getIsbn());
					pstmt.setString(2, book.getTitle());
					pstmt.setString(3, book.getDescription());
					pstmt.setBoolean(4, book.isRented());
					pstmt.setDate(5, book.getAddLib());
					
					
					if (pstmt.executeUpdate() > 0) {
						return true;
					};
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return false;
	}
	
	public boolean updateBook(BookModel book) {
		try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_BOOK)) {
			//UPDATE book SET isbn = ?, title = ?, descr = ?, rented = ?, added_to_library = ? WHERE isbn = ?
			
			pstmt.setInt(1, book.getIsbn());
			pstmt.setString(2, book.getTitle());
			pstmt.setString(3, book.getDescription());
			pstmt.setBoolean(4, book.isRented());
			pstmt.setDate(5, book.getAddLib());
			pstmt.setInt(6, book.getIsbn());
			// at least one row updated
			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteBook(int isbn) {
		try (PreparedStatement pstmt = connection.prepareStatement(DELETE_BOOK)) {
			//DELETE FROM book WHERE isbn = ?
			pstmt.setInt(1, isbn);

			// at least one row deleted
			if (pstmt.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
