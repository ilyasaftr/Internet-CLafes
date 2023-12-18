package model;

import java.time.LocalTime;

public class TransactionDetail {
	private Integer TransactionID, PC_ID, TransactionDetailID;
	private String CustomerName;
	private LocalTime BookedTime;

	public TransactionDetail(Integer transactionID, Integer pC_ID, Integer transactionDetailID, String customerName,
			LocalTime bookedTime) {
		super();
		TransactionID = transactionID;
		PC_ID = pC_ID;
		TransactionDetailID = transactionDetailID;
		CustomerName = customerName;
		BookedTime = bookedTime;
	}

	public Integer getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public LocalTime getBookedTime() {
		return BookedTime;
	}

	public void setBookedTime(LocalTime bookedTime) {
		BookedTime = bookedTime;
	}

	public Integer getTransactionDetailID() {
		return TransactionDetailID;
	}

	public void setTransactionDetailID(Integer transactionDetailID) {
		TransactionDetailID = transactionDetailID;
	}

	
	
}
