package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

import database.Connect;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;

public class TransactionModel {
	// TransactionModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile TransactionModel instance = null;
	
	private TransactionModel() {
		
	}
	
	public static TransactionModel getInstance() {
		if(instance == null) {
			synchronized (TransactionModel.class) {
				if(instance == null) {
					 instance = new TransactionModel();
				}
			}
		}
		
		return instance;
	}

	// Method untuk mendapatkan list isinya semua data transaction header
	public Vector<TransactionHeader> getAllTransactionHeaderData() {
		Vector<TransactionHeader> transList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `transactionheader`";

		ResultSet rs = con.execQuery(query);

		try {
			while(rs.next()) {
				Integer TransactionID = rs.getInt("TransactionID");
				Integer StaffID = rs.getInt("StaffID");
				LocalDate TransactionDate = rs.getObject("TransactionDate", LocalDate.class);
				String StaffName = rs.getString("StaffName");

				transList.add(new TransactionHeader(TransactionID, StaffID, TransactionDate, StaffName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transList;
	}

	public void addTransaction(int transactionID, Vector<PCBook> listPcBook, int staffID) {
		
	}

	public Vector<TransactionDetail> getAllTransactionDetail(int transactionID) {
		Vector<TransactionDetail> transList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `transactiondetail` WHERE `TransactionID` = ?";
		PreparedStatement ps = con.prepareStatement(query);

		

		try {
			ps.setInt(1, transactionID);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				Integer TransactionID = rs.getInt("TransactionID");
				Integer PC_ID = rs.getInt("PC_ID");
				String CustomerName = rs.getString("CustomerName");
				LocalTime BookedTime = rs.getObject("BookedTime", LocalTime.class);

				transList.add(new TransactionDetail(TransactionID, PC_ID, CustomerName, BookedTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transList;
	}

	public Vector<TransactionDetail> getUserTransactionDetail(int userID) {
		// TODO Auto-generated method stub
		return null;
	}
}
