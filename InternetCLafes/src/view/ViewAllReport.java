package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Report;

public class ViewAllReport {
	public class ViewAllReportVar{
		public Stage stage;
		Scene scene;
		
		BorderPane bp;
		VBox vb;
		
		Label titleLbl;
		
		TableView<Report> reportTable;
		TableColumn<Report, Integer> reportIDCol, pcIDCol;
		TableColumn<Report, String> userRoleCol, reportDateCol, reportNoteCol;
	}
	
	public ViewAllReport(Stage stage) {
		ViewAllReportVar components = new ViewAllReportVar();
		initialize(components);
		
		components.stage = stage;
		components.stage.setScene(components.scene);
		components.stage.show();
	}

	private void initialize(ViewAllReportVar components) {
		
		components.titleLbl = new Label("View All Reports");
		
		initializeTable(components);
		getData(components);
		setStyle(components);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.titleLbl, components.reportTable);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.vb);
		
		components.scene = new Scene(components.bp);
	}

	private void setStyle(ViewAllReportVar components) {
		components.pcIDCol.minWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(1));
		components.reportIDCol.minWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(1));
		components.reportDateCol.minWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(2));
		components.reportNoteCol.minWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(14));
		components.userRoleCol.minWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(2));
	}

	private void getData(ViewAllReportVar components) {
		ArrayList<Report> reportList = new ArrayList<>();
		Connect con = Connect.getInstance();
		
		ResultSet rs = con.execQuery("SELECT * FROM `report`");
		
		try {
			while(rs.next()) {
				Integer Report_ID = rs.getInt(1);
				Integer PC_ID = rs.getInt(2);
				String UserRole = rs.getString(3);
				String ReportDate = rs.getString(4);
				String ReportNote = rs.getString(5);
				
				reportList.add(new Report(Report_ID, PC_ID, UserRole, ReportNote, ReportDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(Report report : reportList) {
			components.reportTable.getItems().add(report);
		}
	}

	private void initializeTable(ViewAllReportVar components) {
		components.reportTable = new TableView<>();
		
		components.pcIDCol = new TableColumn<>("PC ID");
		components.reportIDCol = new TableColumn<>("Report ID");
		components.reportDateCol = new TableColumn<>("Report Date");
		components.reportNoteCol = new TableColumn<>("Report Note");
		components.userRoleCol = new TableColumn<>("User Role");
		
		components.reportTable.getColumns().add(components.reportIDCol);
		components.reportTable.getColumns().add(components.pcIDCol);
		components.reportTable.getColumns().add(components.userRoleCol);
		components.reportTable.getColumns().add(components.reportDateCol);
		components.reportTable.getColumns().add(components.reportNoteCol);
		
		components.pcIDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		components.reportIDCol.setCellValueFactory(new PropertyValueFactory<>("Report_ID"));
		components.reportDateCol.setCellValueFactory(new PropertyValueFactory<>("ReportDate"));
		components.reportNoteCol.setCellValueFactory(new PropertyValueFactory<>("ReportNote"));
		components.userRoleCol.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
	}
}
