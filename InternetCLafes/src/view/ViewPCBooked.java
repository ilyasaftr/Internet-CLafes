package view;

import java.time.LocalDate;
import java.util.Vector;

import controller.OperatorController;
import controller.PCBookController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PCBook;
import model.User;

public class ViewPCBooked {
	/*
	 * ViewAllStaffVar berisi semua komponen UI yang digunakan di page ViewAllStaff
	 */
	public class ViewPCBookedVar{
		public Vector<PCBook> pcBookList;
		
		Scene scene;

		BorderPane bp;

		Label titleLbl;
		
		GridPane gp;
		
		public Button btnFinishBook, btnCancelBook;
		
		ScrollPane sp;
		
		VBox vb;
		HBox hb;
		
		public Alert noRowAlert, invalidDataAlert, successAlert;
		
		public TableView<PCBook> pcBookTable;
		TableColumn<PCBook, Integer> bookIDCol, pcIDCol, userIDCol;
		TableColumn<PCBook, LocalDate> bookedDateCol;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewPCBookedVar components) {

		initializeAssign(components);

		components.btnFinishBook = new Button("Finish Book");
		components.btnCancelBook = new Button("Cancel Book");
		
		components.hb = new HBox();
		components.hb.getChildren().addAll(components.btnFinishBook, components.btnCancelBook);
		
		components.vb.getChildren().add(components.hb);
	}

	private void initializeAssign(ViewPCBookedVar components) {
		components.titleLbl = new Label("View All PC Booked");

		initializeTable(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.pcBookTable);
		components.sp.setFitToWidth(true);
		
		components.vb = new VBox();
		components.vb.getChildren().add(components.sp);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);

		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewPCBookedVar components) {
		
		setStyleAssign(components);
		
		components.hb.setSpacing(15);
		components.hb.setPadding(new Insets(10));
	}

	private void setStyleAssign(ViewPCBookedVar components) {
		// atur ukuran lebar setiap kolom menggunakan binding
		components.bookIDCol.prefWidthProperty().bind(components.pcBookTable.widthProperty().divide(8).multiply(2));
		components.pcIDCol.prefWidthProperty().bind(components.pcBookTable.widthProperty().divide(8).multiply(2));
		components.userIDCol.prefWidthProperty().bind(components.pcBookTable.widthProperty().divide(8).multiply(2));
		components.bookedDateCol.prefWidthProperty().bind(components.pcBookTable.widthProperty().divide(8).multiply(2));

		components.vb.setSpacing(30);
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel dan kolom-kolomnya juga
	 */

	private void initializeTable(ViewPCBookedVar components) {
		components.pcBookTable = new TableView<>();

		components.bookIDCol = new TableColumn<>("Book ID");
		components.pcIDCol = new TableColumn<>("PC ID");
		components.userIDCol = new TableColumn<>("User ID");
		components.bookedDateCol = new TableColumn<>("BookedDate");

		components.pcBookTable.getColumns().add(components.bookIDCol);
		components.pcBookTable.getColumns().add(components.pcIDCol);
		components.pcBookTable.getColumns().add(components.userIDCol);
		components.pcBookTable.getColumns().add(components.bookedDateCol);

		components.bookIDCol.setCellValueFactory(new PropertyValueFactory<>("Book_ID"));
		components.pcIDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		components.userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
		components.bookedDateCol.setCellValueFactory(new PropertyValueFactory<>("BookedDate"));
		
		PCBookController pc = PCBookController.getInstance();
		pc.getData(components);
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(User user) {
		ViewPCBookedVar components = new ViewPCBookedVar();
		initialize(components);
		initializeAlert(components);
		
		setStyle(components);
		
		PCBookController pc = PCBookController.getInstance();
		pc.addViewPCBookedHandler(components, user.getUserID());
		
		components.bp.setTop(OperatorController.instance.menuOperator.menuBar);

		return components.scene;
	}
	
	public Scene initPageAssign(User user) {
		ViewPCBookedVar components = new ViewPCBookedVar();
		initializeAssign(components);
		initializeAlert(components);
		
		setStyleAssign(components);
		
		PCBookController pc = PCBookController.getInstance();
		pc.addViewAssignUserHandler(components, user.getUserID());
		
		components.bp.setTop(OperatorController.instance.menuOperator.menuBar);

		return components.scene;
	}

	// inisialisasi alert baik itu kalau ga ada staff yang di-select atau kalau data invalid
	public void initializeAlert(ViewPCBookedVar components) {
		components.noRowAlert = new Alert(AlertType.ERROR);
		components.noRowAlert.setTitle("Error");
		components.noRowAlert.setContentText("You must select a row from the table!");
		
		components.invalidDataAlert = new Alert(AlertType.ERROR);
		components.invalidDataAlert.setTitle("Error");

		components.successAlert = new Alert(AlertType.INFORMATION);
		components.successAlert.setTitle("Success");
	}

}