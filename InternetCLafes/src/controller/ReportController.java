package controller;

import java.util.Vector;

import dao.ReportModel;
import model.Report;

public class ReportController {
	// Report Controller menggunakan Singleton agar hanya satu instance yang terpakai di app.
	public static volatile ReportController instance = null;
	
	private ReportController() {
		
	}
	
	public static ReportController getInstance() {
		if(instance == null) {
			synchronized (ReportController.class) {
				if(instance == null) {
					 instance = new ReportController();
				}
			}
		}
		
		return instance;
	}
	
	// Akses model untuk mengakses data Report dari database
	ReportModel reportModel = ReportModel.getInstance();
	
	// Method untuk mendapatkan list isinya semua data report
	public Vector<Report> getAllReportData() {
		return reportModel.getAllReportData();
	}
	
	// Method untuk menambahkan record Report ke database melalui model
	public void addNewReport(String UserRole, Integer PcID, String ReportNote) {
		reportModel.addNewReport(UserRole, PcID, ReportNote);
	}
}
