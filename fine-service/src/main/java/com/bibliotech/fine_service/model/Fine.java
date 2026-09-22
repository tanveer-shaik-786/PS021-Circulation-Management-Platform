package com.bibliotech.fine_service.model;

import jakarta.persistence.*;

@Entity
public class Fine {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fineId;
    private int rentalId;
    private double amount;
    private String status;
    
	public int getFineId() {
		return fineId;
	}
	public void setFineId(int fineId) {
		this.fineId = fineId;
	}
	public int getRentalId() {
		return rentalId;
	}
	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Fine [fineId=" + fineId + ", rentalId=" + rentalId + ", amount=" + amount + ", status=" + status + "]";
	}
	
	public Fine(int rentalId, double amount, String status) {
		super();
		this.rentalId = rentalId;
		this.amount = amount;
		this.status = status;
	}
	
	public Fine() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
