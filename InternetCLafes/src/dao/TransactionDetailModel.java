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

public class TransactionDetailModel {
	// TransactionDetailModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile TransactionDetailModel instance = null;
	
	private TransactionDetailModel() {
		
	}
	
	public static TransactionDetailModel getInstance() {
		if(instance == null) {
			synchronized (TransactionDetailModel.class) {
				if(instance == null) {
					 instance = new TransactionDetailModel();
				}
			}
		}
		
		return instance;
	}
	
	// method untuk mendapatkan transaction detail dari transaction id 
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

	// method untuk mendapatkan transaction detail dari customer id
	public Vector<TransactionDetail> getUserTransactionDetail(int userID) {
		Vector<TransactionDetail> transList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `transactiondetail` td JOIN `user` u ON td.`CustomerName` = u.`UserName` WHERE u.`UserID` = ?;";
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, userID);
			
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
	
	// method untuk menambahkan transaction detail
	public void addNewTransactionDetail(int transactionID, Vector<PCBook> listPcBook) {
		
	}
}
