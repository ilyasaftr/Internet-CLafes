package controller;

import java.util.Vector;

import dao.ReportModel;
import model.Report;
import view.MakeReport.MakeReportVar;

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

	// Event handler add report
	public void addAddReportHandler(MakeReportVar components, String role) {
		components.btnSubmit.setOnAction(e -> {
			// Validasi apa PC ID dan ReportNote terisi
			if(components.PC_IDCB.getValue() == null) {
				components.errorAlert.setContentText("PC ID can not be empty");
				components.errorAlert.showAndWait();
			}
			else if(components.ReportNoteTxt.getText().isBlank()) {
				components.errorAlert.setContentText("Report Note can not be empty");
				components.errorAlert.showAndWait();
			}
			else {
				// Kalau valid, tambahkan report ke database dan tampilkan confirmation message
				addNewReport(role, components.PC_IDCB.getValue(), components.ReportNoteTxt.getText());
				components.successAlert.setContentText("Report successfully created");
				components.successAlert.showAndWait();
			}
		});
	}
}
