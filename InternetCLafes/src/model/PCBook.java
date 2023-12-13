package model;

import java.time.LocalDate;

public class PCBook {
	private Integer Book_ID, PC_ID, UserID;
	private LocalDate BookedDate;

	public PCBook(Integer book_ID, Integer pC_ID, Integer userID, LocalDate bookedDate) {
		super();
		Book_ID = book_ID;
		PC_ID = pC_ID;
		UserID = userID;
		BookedDate = bookedDate;
	}

	public Integer getBook_ID() {
		return Book_ID;
	}

	public void setBook_ID(Integer book_ID) {
		Book_ID = book_ID;
	}

	public Integer getPC_ID() {
		return PC_ID;
	}

	public void setPC_ID(Integer pC_ID) {
		PC_ID = pC_ID;
	}

	public Integer getUserID() {
		return UserID;
	}

	public void setUserID(Integer userID) {
		UserID = userID;
	}

	public LocalDate getBookedDate() {
		return BookedDate;
	}

	public void setBookedDate(LocalDate bookedDate) {
		BookedDate = bookedDate;
	}


}
