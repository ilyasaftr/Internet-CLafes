package controller;

import java.util.Vector;

import dao.ReportModel;
import model.Report;

public class ReportController {
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
	
	ReportModel reportModel = ReportModel.getInstance();
	
	public Vector<Report> getAllReportData() {
		return reportModel.getAllReportData();
	}
	
	public void addNewReport(String UserRole, Integer PcID, String ReportNote) {
		reportModel.addNewReport(UserRole, PcID, ReportNote);
	}
}
