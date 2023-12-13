package model;

import java.time.LocalDate;

public class TransactionHeader {
	private Integer TransactionID, StaffID;
	private LocalDate TransactionDate;
	private String StaffName;

	public TransactionHeader(Integer transactionID, Integer staffID, LocalDate transactionDate, String staffName) {
		super();
		TransactionID = transactionID;
		StaffID = staffID;
		TransactionDate = transactionDate;
		StaffName = staffName;
	}

	public Integer getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}

	public Integer getStaffID() {
		return StaffID;
	}

	public void setStaffID(Integer staffID) {
		StaffID = staffID;
	}

	public LocalDate getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		TransactionDate = transactionDate;
	}

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}


}
