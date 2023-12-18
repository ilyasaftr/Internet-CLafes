package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Vector;

import dao.TransactionDetailModel;
import dao.TransactionHeaderModel;
import database.Connect;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.PCBook;
import model.TransactionDetail;
import model.TransactionHeader;
import view.ViewAllTransaction.ViewAllTransactionVar;

public class TransactionController {
	// Transaction Controller menggunakan Singleton agar hanya satu instance yang
	// terpakai di app.
	public static volatile TransactionController instance = null;

	private TransactionController() {

	}

	public static TransactionController getInstance() {
		if (instance == null) {
			synchronized (TransactionController.class) {
				if (instance == null) {
					instance = new TransactionController();
				}
			}
		}

		return instance;
	}

	// Akses model untuk mengakses data transaction header dan detail dari database
	TransactionHeaderModel transactionHeaderModel = TransactionHeaderModel.getInstance();
	TransactionDetailModel transactionDetailModel = TransactionDetailModel.getInstance();

	// Method untuk mendapatkan list isinya semua data transaction header
	public Vector<TransactionHeader> getAllTransactionHeaderData() {
		return transactionHeaderModel.getAllTransactionHeaderData();
	}
	
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
	
	public void addTransactionDetail(int TransactionID, List<PCBook> pcBookList) {
		Connect con = Connect.getInstance();
		for (PCBook pcBook : pcBookList) {
			String insertTransactionQuery = "INSERT INTO `transactiondetail`(`TransactionID`, `PC_ID`, `CustomerName`, BookedTime) VALUES (?, ?, ?, ?)";
			PreparedStatement insertPs = con.prepareStatement(insertTransactionQuery);

			try {
				insertPs.setInt(1, TransactionID);
				insertPs.setInt(2, pcBook.getPC_ID());
				insertPs.setString(3, "A");
				LocalTime currentTime = LocalTime.now();
				Time sqlTime = Time.valueOf(currentTime);
				insertPs.setTime(4, sqlTime);
				insertPs.executeUpdate();
				
				PCBookController pcbControlller = PCBookController.getInstance();
				pcbControlller.deleteBookData(pcBook.getBook_ID());
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}

	// Method untuk menambahkan record transaction header dan detail ke database
	public void addTransaction(List<PCBook> pcBookList, int StaffID) {
		LocalDate currentDate = LocalDate.now();
		int transactionId = addNewTransactionHeader(StaffID, currentDate);
		addTransactionDetail(transactionId, pcBookList);
	}

	// Method untuk mendapatkan semua transaction detail dari transaction id yang
	// bersangkutan
	public Vector<TransactionDetail> getAllTransactionDetail(int TransactionID) {
		return transactionDetailModel.getAllTransactionDetail(TransactionID);
	}

	// Method untuk mendapatkan semua transaction detail dari user id yang
	// bersangkutan
	public Vector<TransactionDetail> getUserTransactionDetail(int UserID) {
		return transactionDetailModel.getUserTransactionDetail(UserID);
	}

	// event handler view all transaction (admin)
	public void addViewTransactionDetailHandler(ViewAllTransactionVar components) {
		// mengatur logic kalau ada record di table transaction header yang di select
		components.transactionTable.setOnMouseClicked(e -> {
			TableSelectionModel<TransactionHeader> tableSelectionModel = components.transactionTable
					.getSelectionModel();

			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);

			// menampung selected item dari table
			TransactionHeader th = tableSelectionModel.getSelectedItem();

			// kalau transaction header ada yang di-select
			// buat window transaction detail baru
			if (th != null) {
				AdminController.getInstance().createTransactionDetailWindow(th.getTransactionID());
			}
		});
	}
}