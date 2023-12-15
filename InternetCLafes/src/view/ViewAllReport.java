package view;

import java.util.Vector;

import controller.AdminController;
import controller.ReportController;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Report;

public class ViewAllReport {

	/*
	 * ViewAllReportVar berisi semua komponen UI yang digunakan di page ViewAllReport
	 */
	public class ViewAllReportVar{
		Scene scene;

		BorderPane bp;

		Label titleLbl;

		ScrollPane sp;
		
		TableView<Report> reportTable;
		TableColumn<Report, Integer> reportIDCol, pcIDCol;
		TableColumn<Report, String> userRoleCol, reportDateCol, reportNoteCol;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewAllReportVar components) {

		components.titleLbl = new Label("View All Reports");

		initializeTable(components);
		getData(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.reportTable);
		components.sp.setFitToWidth(true);

		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.sp);

		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewAllReportVar components) {
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.pcIDCol.prefWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(2));
		components.reportIDCol.prefWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(2));
		components.reportDateCol.prefWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(2));
		components.reportNoteCol.prefWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(12));
		components.userRoleCol.prefWidthProperty().bind(components.reportTable.widthProperty().divide(20).multiply(2));
	}

	/*
	 * getData digunakan untuk mendapatkan data report semua yang akan dimasukkan ke tabel
	 */
	private void getData(ViewAllReportVar components) {
		ReportController reportControl = ReportController.getInstance();
		Vector<Report> reportList = reportControl.getAllReportData();

		for(Report report : reportList) {
			components.reportTable.getItems().add(report);
		}
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel report, kolom-kolomnya juga
	 */
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
		
		// membuat agar reportNoteCol bisa text wrap
		components.reportNoteCol.setCellFactory(new Callback<TableColumn<Report,String>, TableCell<Report,String>>() {
			
			@Override
			public TableCell<Report, String> call(TableColumn<Report, String> param) {
				TableCell<Report, String> cell = new TableCell<>();
			    Text text = new Text();
			    cell.setGraphic(text);
			    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			    text.wrappingWidthProperty().bind(components.reportNoteCol.widthProperty());
			    text.textProperty().bind(cell.itemProperty());
			    return cell;
			}
		});
		
		// membuat agar userRoleCol bisa text wrap
		components.userRoleCol.setCellFactory(new Callback<TableColumn<Report,String>, TableCell<Report,String>>() {
			
			@Override
			public TableCell<Report, String> call(TableColumn<Report, String> param) {
				TableCell<Report, String> cell = new TableCell<>();
			    Text text = new Text();
			    cell.setGraphic(text);
			    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			    text.wrappingWidthProperty().bind(components.userRoleCol.widthProperty());
			    text.textProperty().bind(cell.itemProperty());
			    return cell;
			}
		});
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(String role) {
		ViewAllReportVar components = new ViewAllReportVar();
		initialize(components);
		setStyle(components);
		
		components.bp.setTop(AdminController.getInstance().menuAdmin.menuBar);

		return components.scene;
	}
}
