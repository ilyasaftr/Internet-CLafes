package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import database.Connect;
import model.TransactionHeader;

public class TransactionHeaderModel {
	// TransactionHeaderModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile TransactionHeaderModel instance = null;
	
	private TransactionHeaderModel() {
		
	}
	
	public static TransactionHeaderModel getInstance() {
		if(instance == null) {
			synchronized (TransactionHeaderModel.class) {
				if(instance == null) {
					 instance = new TransactionHeaderModel();
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

	// method untuk menambahkan transaction header
	public int addNewTransactionHeader(int StafFID, LocalDate TransactionDate) {
		Connect con = Connect.getInstance();
		
		String selectStaffNameQuery = "SELECT `UserName` FROM `user` WHERE `UserID` = ?";
		String insertTransactionQuery = "INSERT INTO `transactionheader`(`StaffID`, `StaffName`, `TransactionDate`) VALUES (?, ?, ?)";
		
        PreparedStatement selectPs = con.prepareStatement(selectStaffNameQuery);
		PreparedStatement insertPs = con.prepareStatement(insertTransactionQuery);
        
		try {
			selectPs.setInt(1, StafFID);
			insertPs.setInt(1, StafFID);
			
            try (ResultSet resultSet = selectPs.executeQuery()) {
                if (resultSet.next()) {
                    String staffName = resultSet.getString("UserName");
                    insertPs.setString(2, staffName);
                } else {
                    throw new SQLException("Get Name failed, no ID obtained.");
                }
            }

			Date tDate = Date.valueOf(TransactionDate);
			insertPs.setDate(3, tDate);
			insertPs.executeUpdate();
			
			try (ResultSet generatedKeys = insertPs.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating PC failed, no ID obtained.");
                }
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
}
