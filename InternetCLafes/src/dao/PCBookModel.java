package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import database.Connect;
import model.PCBook;

public class PCBookModel {
	// PCBookModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile PCBookModel instance = null;
	
	private PCBookModel() {
		
	}
	
	public static PCBookModel getInstance() {
		if(instance == null) {
			synchronized (PCBookModel.class) {
				if(instance == null) {
					 instance = new PCBookModel();
				}
			}
		}
		
		return instance;
	}
	
	// Mendapatkan list PCBook yang tanggalnya masih tanggal-tanggal ke depan dari sekarang berdasarkan PC ID
	public Vector<PCBook> GetPCBookedData(Integer PcID, LocalDate date) {
		Vector<PCBook> bookList = new Vector<>();

		Connect con = Connect.getInstance();
		
		String query = "SELECT * FROM `pcbook` WHERE `PC_ID` = ? AND `BookedDate` > ?;";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, PcID);
			ps.setObject(2, date);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				Integer Book_ID = rs.getInt("Book_ID");
				Integer PC_ID = rs.getInt("PC_ID");
				Integer UserID = rs.getInt("UserID");
				LocalDate BookedDate = rs.getObject("BookedDate", LocalDate.class);
				bookList.add(new PCBook(Book_ID, PC_ID, UserID, BookedDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}
	
	public void addNewPCBook(int PcID, int UserId, LocalDate BookDP) {
		Connect con = Connect.getInstance();

		String query = "INSERT INTO `pcbook`(`PC_ID`, `UserID`, `BookedDate`) VALUES (?, ?, ?)";

		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, PcID);
			ps.setInt(2, UserId);
			
			Date sqlBookDP = Date.valueOf(BookDP);
			ps.setDate(3, sqlBookDP);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Pindahkan user ke PC baru
	public void assignUsertoNewPC(Integer bookID, int newPCID) {
		Connect con = Connect.getInstance();
		
		String query = "UPDATE `pcbook` SET `PC_ID`=? WHERE `Book_ID`=?";
		
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, newPCID);
			ps.setInt(2, bookID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
