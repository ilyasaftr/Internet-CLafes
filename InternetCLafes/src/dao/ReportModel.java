package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import database.Connect;
import model.Report;

public class ReportModel {
	// ReportModel menerapkan Singleton agar instance yang digunakan hanya satu dalam seluruh app.
	
	public static volatile ReportModel instance = null;
	
	private ReportModel() {
		
	}
	
	public static ReportModel getInstance() {
		if(instance == null) {
			synchronized (ReportModel.class) {
				if(instance == null) {
					 instance = new ReportModel();
				}
			}
		}
		
		return instance;
	}
	
	// addNewReport digunakan untuk menambahkan record Report ke database.
	public void addNewReport(String UserRole, Integer PcID, String ReportNote) {
		Connect con = Connect.getInstance();

		String query = "INSERT INTO `report`(`UserRole`, `PC_ID`, `ReportNote`, `ReportDate`) VALUES (?, ?, ?, ?)";

		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setString(1, UserRole);
			ps.setInt(2, PcID);
			ps.setString(3, ReportNote);
			ps.setObject(4, LocalDate.now());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// getAllReportData digunakan untuk me-retrieve semua record Report dari SQL database
	public Vector<Report> getAllReportData() {
		Vector<Report> reportList = new Vector<>();

		Connect con = Connect.getInstance();
		String query = "SELECT * FROM `report`";

		ResultSet rs = con.execQuery(query);

		try {
			while(rs.next()) {
				Integer Report_ID = rs.getInt(1);
				String UserRole = rs.getString(2);
				Integer PC_ID = rs.getInt(3);
				String ReportNote = rs.getString(4);
				LocalDate ReportDate = rs.getObject(5, LocalDate.class);

				reportList.add(new Report(Report_ID, PC_ID, UserRole, ReportNote, ReportDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reportList;
	}
}
