package com.cognixia.jump.models;

import java.sql.Date;

public class Book_CheckoutModel {
	private int checkoutId;
	private int patronId;
	private String isbn;
	private Date checkedOutDate;
	private Date dueDate;
	private Date returnedDate;
	
	public Book_CheckoutModel(int checkoutId, int patronId, String isbn, Date checkedOutDate, Date dueDate,
			Date returnedDate) {
		super();
		this.checkoutId = checkoutId;
		this.patronId = patronId;
		this.isbn = isbn;
		this.checkedOutDate = checkedOutDate;
		this.dueDate = dueDate;
		this.returnedDate = returnedDate;
	}
	
	public Book_CheckoutModel(int patronId, String isbn, Date checkedOutDate, Date dueDate) {
		super();
		this.patronId = patronId;
		this.isbn = isbn;
		this.checkedOutDate = checkedOutDate;
		this.dueDate = dueDate;
	}

	public int getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(int checkoutId) {
		this.checkoutId = checkoutId;
	}

	public int getPatronId() {
		return patronId;
	}

	public void setPatronId(int patronId) {
		this.patronId = patronId;
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

	@Override
	public String toString() {
		return "Book_CheckoutModel [checkoutId=" + checkoutId + ", patronId=" + patronId + ", isbn=" + isbn
				+ ", checkedOutDate=" + checkedOutDate + ", dueDate=" + dueDate + ", returnedDate=" + returnedDate
				+ "]";
	}
	
	
	
	
}
