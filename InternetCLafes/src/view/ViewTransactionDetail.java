package view;

import java.time.LocalTime;
import java.util.Vector;

import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.TransactionDetail;

public class ViewTransactionDetail {
	/*
	 * ViewTransactionDetailVar berisi semua komponen UI yang digunakan di page ViewTransactionDetail
	 * Berbeda dengan ViewCustomerTransaction, page ini untuk view transaction detail berdasarkan transaction ID bukan customer ID
	 */
	public class ViewTransactionDetailVar {
		Integer transactionID;
		
		public Stage stage;
		Scene scene;

		VBox vb;

		Label titleLbl;

		ScrollPane sp;
		
		public TableView<TransactionDetail> transactionTable;
		TableColumn<TransactionDetail, Integer> TransactionDetailIDCol;
		TableColumn<TransactionDetail, Integer> TransactionIDCol;
		TableColumn<TransactionDetail, Integer> PC_IDCol;
		TableColumn<TransactionDetail, String> CustomerNameCol;
		TableColumn<TransactionDetail, LocalTime> BookedTimeCol;
	}
	
	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewTransactionDetailVar components) {

		components.titleLbl = new Label("View Transaction Details");

		initializeTable(components);
		getData(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.transactionTable);
		components.sp.setFitToWidth(true);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.titleLbl, components.sp);

		components.scene = new Scene(components.vb);
	}

	private void getData(ViewTransactionDetailVar components) {
		TransactionController transControl = TransactionController.getInstance();
		Vector<TransactionDetail> transList = transControl.getAllTransactionDetail(components.transactionID);

		for(TransactionDetail trans : transList) {
			components.transactionTable.getItems().add(trans);
		}
	}

	private void initializeTable(ViewTransactionDetailVar components) {
		components.transactionTable = new TableView<>();

		components.TransactionDetailIDCol = new TableColumn<>("Transaction Detail ID");
		components.TransactionIDCol = new TableColumn<>("Transaction ID");
		components.PC_IDCol = new TableColumn<>("PC ID");
		components.CustomerNameCol = new TableColumn<>("Customer Name");
		components.BookedTimeCol = new TableColumn<>("Booked Time");

		components.transactionTable.getColumns().add(components.TransactionDetailIDCol);
		components.transactionTable.getColumns().add(components.TransactionIDCol);
		components.transactionTable.getColumns().add(components.PC_IDCol);
		components.transactionTable.getColumns().add(components.CustomerNameCol);
		components.transactionTable.getColumns().add(components.BookedTimeCol);
		
		components.TransactionDetailIDCol.setCellValueFactory(new PropertyValueFactory<>("TransactionDetailID"));
		components.TransactionIDCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
		components.PC_IDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		components.CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
		components.BookedTimeCol.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));
	}
	
	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewTransactionDetailVar components) {
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.TransactionDetailIDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(10).multiply(3));
		components.TransactionIDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(10).multiply(1));
		components.PC_IDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(10).multiply(1));
		components.CustomerNameCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(10).multiply(3));
		components.BookedTimeCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(10).multiply(2));
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
	}
	
	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public void initPage(String role, Integer transID) {
		ViewTransactionDetailVar components = new ViewTransactionDetailVar();
		
		components.transactionID = transID;
		
		initialize(components);
		setStyle(components);
		
		components.stage = new Stage();
		components.stage.setScene(components.scene);
		components.stage.show();
	}
}
