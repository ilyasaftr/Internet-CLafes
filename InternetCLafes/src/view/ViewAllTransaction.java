package view;

import java.time.LocalDate;
import java.util.Vector;

import controller.AdminController;
import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.TransactionHeader;

public class ViewAllTransaction {

	/*
	 * ViewAllTransactionVar berisi semua komponen UI yang digunakan di page ViewAllTransaction
	 */
	public class ViewAllTransactionVar{
		Scene scene;

		BorderPane bp;

		Label titleLbl;

		ScrollPane sp;
		
		public TableView<TransactionHeader> transactionTable;
		TableColumn<TransactionHeader, Integer> TransactionIDCol;
		TableColumn<TransactionHeader, Integer> StaffIDCol;
		TableColumn<TransactionHeader, LocalDate> TransactionDateCol;
		TableColumn<TransactionHeader, String> StaffNameCol;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewAllTransactionVar components) {

		components.titleLbl = new Label("View All Transactions");

		initializeTable(components);
		getData(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.transactionTable);
		components.sp.setFitToWidth(true);

		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.sp);

		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewAllTransactionVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.TransactionIDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(1));
		components.StaffIDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(1));
		components.TransactionDateCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(3));
		components.StaffNameCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(3));
	}

	/*
	 * getData digunakan untuk mendapatkan data report semua yang akan dimasukkan ke tabel
	 */
	private void getData(ViewAllTransactionVar components) {
		TransactionController transControl = TransactionController.getInstance();
		Vector<TransactionHeader> transList = transControl.getAllTransactionHeaderData();

		for(TransactionHeader trans : transList) {
			components.transactionTable.getItems().add(trans);
		}
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel, kolom-kolomnya juga
	 */
	private void initializeTable(ViewAllTransactionVar components) {
		components.transactionTable = new TableView<>();

		components.TransactionIDCol = new TableColumn<>("Transaction ID");
		components.StaffIDCol = new TableColumn<>("Staff ID");
		components.StaffNameCol = new TableColumn<>("Staff Name");
		components.TransactionDateCol = new TableColumn<>("Transaction Date");

		components.transactionTable.getColumns().add(components.TransactionIDCol);
		components.transactionTable.getColumns().add(components.StaffIDCol);
		components.transactionTable.getColumns().add(components.StaffNameCol);
		components.transactionTable.getColumns().add(components.TransactionDateCol);
		
		components.TransactionIDCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
		components.StaffIDCol.setCellValueFactory(new PropertyValueFactory<>("StaffID"));
		components.StaffNameCol.setCellValueFactory(new PropertyValueFactory<>("StaffName"));
		components.TransactionDateCol.setCellValueFactory(new PropertyValueFactory<>("TransactionDate"));
		
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(String role) {
		ViewAllTransactionVar components = new ViewAllTransactionVar();
		initialize(components);
		setStyle(components);
		
		components.bp.setTop(AdminController.getInstance().menuAdmin.menuBar);
		
		TransactionController tc = TransactionController.getInstance();
		tc.addViewTransactionDetailHandler(components);

		return components.scene;
	}
}
