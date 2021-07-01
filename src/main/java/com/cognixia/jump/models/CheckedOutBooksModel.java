package com.cognixia.jump.models;

import java.sql.Date;

public class CheckedOutBooksModel {
	private String isbn;
	Date checkedOutDate;
	Date dueDate;
	Date returnedDate;
	String title;
	String description;
	
	@Override
	public String toString() {
		return "CheckedOutBooksModel [isbn=" + isbn + ", checkedOutDate=" + checkedOutDate + ", dueDate=" + dueDate
				+ ", returnedDate=" + returnedDate + ", title=" + title + ", description=" + description + "]";
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getCheckedOutDate() {
		return checkedOutDate;
	}

	public void setCheckedOutDate(Date checkedOutDate) {
		this.checkedOutDate = checkedOutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
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

	public CheckedOutBooksModel(String isbn, Date checkedOutDate, Date dueDate, Date returnedDate, String title,
			String description) {
		super();
		this.isbn = isbn;
		this.checkedOutDate = checkedOutDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
		this.title = title;
		this.description = description;
	}
	
	public CheckedOutBooksModel(String isbn, Date checkedOutDate, Date dueDate, String title,
			String description) {
		super();
		this.isbn = isbn;
		this.checkedOutDate = checkedOutDate;
		this.dueDate = dueDate;
		this.title = title;
		this.description = description;
	}
}
