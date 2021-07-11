package com.cognixia.jump.models;

import java.sql.Date;
import java.time.ZonedDateTime;

public class BookModel {
	
	private String isbn;
	private String title;
	private String description;
	private boolean rented;
	private Date addLib;
	
	
	public BookModel(String isbn, String title, String description, boolean rented, Date addLib) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.rented = rented;
		this.addLib = addLib;
	}
	
	public static BookModel createNewBook(String isbn, String title, String description) {
		long currDateInMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		Date today = new Date(currDateInMilli);
		return new BookModel(isbn, title, description, false, today);
	}
	public static BookModel createNewBook() {
		return createNewBook(null, null, null);
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isRented() {
		return rented;
	}


	public void setRented(boolean rented) {
		this.rented = rented;
	}


	public Date getAddLib() {
		return addLib;
	}


	public void setAddLib(Date addLib) {
		this.addLib = addLib;
	}


	@Override
	public String toString() {
		return "BookModel [isbn=" + isbn + ", title=" + title + ", description=" + description + ", rented=" + rented
				+ ", addLib=" + addLib + "]";
	}
	
	
	
	
	
}
