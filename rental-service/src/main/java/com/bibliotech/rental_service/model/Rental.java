package com.bibliotech.rental_service.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Rental {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rentalId;
    private int userId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate returnDate;
    
	public int getRentalId() {
		return rentalId;
	}
	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	
	@Override
	public String toString() {
		return "Rental [rentalId=" + rentalId + ", userId=" + userId + ", bookId=" + bookId + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + "]";
	}
	
	public Rental(int userId, int bookId, LocalDate issueDate, LocalDate returnDate) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
	}
	
	public Rental() {
		super();
		// TODO Auto-generated constructor stub
	}
}
