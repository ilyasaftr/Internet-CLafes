package dao;

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
	public void addNewTransactionHeader(int staffID, LocalDate transactionDate) {
		
	}
}
