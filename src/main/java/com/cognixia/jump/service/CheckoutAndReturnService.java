package com.cognixia.jump.service;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import com.cognixia.jump.dao.BookDao;
import com.cognixia.jump.dao.Book_CheckoutDao;
import com.cognixia.jump.models.BookModel;
import com.cognixia.jump.models.Book_CheckoutModel;

// This class is just a container for static utility methods.
	// No instances are needed, so made it abstract thus explicitly prohibiting instantiation.
abstract public class CheckoutAndReturnService {
	
	private static final Book_CheckoutDao BOOKCHECKOUT_DAO = new Book_CheckoutDao();
	private static final BookDao BOOK_DAO = new BookDao();

	public static boolean checkoutBook(String isbn, int patronId) {
		if (isbn == null || patronId == -1) return false;
		
		BookModel book = BOOK_DAO.getBookByIsbn(isbn);
		if (book == null) return false;

		long currDateInMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		Date checkedoutDate = new Date(currDateInMilli); // <- today
		Date dueDate = new Date(currDateInMilli + TimeUnit.DAYS.toMillis(7)); // <- 1 week from now
		Book_CheckoutModel checkout = new Book_CheckoutModel(patronId, isbn, checkedoutDate, dueDate);
		BOOKCHECKOUT_DAO.addBook(checkout);
		
		book.setRented(true);
		BOOK_DAO.updateBook(book);
		
		return true;
	}
	
	public static boolean returnBook(String isbn, int patronId) {
		if (isbn == null || patronId == -1) return false;
		
		BookModel book = BOOK_DAO.getBookByIsbn(isbn);
		if (book == null) return false;

		long currDateInMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		Date returnedDate = new Date(currDateInMilli); // <- today
		BOOKCHECKOUT_DAO.returnBook(isbn, patronId, returnedDate);
		
		book.setRented(false);
		BOOK_DAO.updateBook(book);
		
		return true;
	}

}
