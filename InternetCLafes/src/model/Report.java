package model;

import java.time.LocalDate;

public class Report {
	private Integer Report_ID, PC_ID, UserID;
	private String UserRole, ReportNote;
	private LocalDate ReportDate;

	public Report(Integer report_ID, Integer pC_ID, Integer userID, String userRole, String reportNote,
			LocalDate reportDate) {
		super();
		Report_ID = report_ID;
		PC_ID = pC_ID;
		UserID = userID;
		UserRole = userRole;
		ReportNote = reportNote;
		ReportDate = reportDate;
	}

	public Integer getReport_ID() {
		return Report_ID;
	}

	public void setReport_ID(Integer report_ID) {
		Report_ID = report_ID;
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

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}

	public String getReportNote() {
		return ReportNote;
	}

	public void setReportNote(String reportNote) {
		ReportNote = reportNote;
	}

	public LocalDate getReportDate() {
		return ReportDate;
	}

	public void setReportDate(LocalDate reportDate) {
		ReportDate = reportDate;
	}


}
