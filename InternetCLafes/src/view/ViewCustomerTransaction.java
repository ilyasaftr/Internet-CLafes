package view;

import java.time.LocalTime;
import java.util.Vector;

import controller.MenuController;
import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.TransactionDetail;
import model.User;

public class ViewCustomerTransaction {
	// halaman untuk menampilkan semua transaction yang bersangkutan dengan seorang user (transaction detail list)
	
	/*
	 * ViewCustomerTransactionVar berisi semua komponen UI yang digunakan di page ViewCustomerTransaction
	 * Berbeda dengan ViewAllTransaction dan ViewTransactionDetail, page ini untuk view transaction detail berdasarkan customer ID, bukan berdasarkan transaction ID, lalu transaction header tidak ditampilkan
	 */
	public class ViewCustomerTransactionVar {
		public User customer;
		
		Scene scene;

		BorderPane bp;

		Label titleLbl;

		ScrollPane sp;
		
		public TableView<TransactionDetail> transactionTable;
		TableColumn<TransactionDetail, Integer> TransactionIDCol;
		TableColumn<TransactionDetail, Integer> PC_IDCol;
		TableColumn<TransactionDetail, String> CustomerNameCol;
		TableColumn<TransactionDetail, LocalTime> BookedTimeCol;
	}
	
	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewCustomerTransactionVar components) {

		components.titleLbl = new Label("View Customer Transactions");

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

	private void getData(ViewCustomerTransactionVar components) {
		TransactionController transControl = TransactionController.getInstance();
		Vector<TransactionDetail> transList = transControl.getUserTransactionDetail(components.customer.getUserID());

		for(TransactionDetail trans : transList) {
			components.transactionTable.getItems().add(trans);
		}
	}

	private void initializeTable(ViewCustomerTransactionVar components) {
		components.transactionTable = new TableView<>();

		components.TransactionIDCol = new TableColumn<>("Transaction ID");
		components.PC_IDCol = new TableColumn<>("PC ID");
		components.CustomerNameCol = new TableColumn<>("Customer Name");
		components.BookedTimeCol = new TableColumn<>("Booked Time");

		components.transactionTable.getColumns().add(components.TransactionIDCol);
		components.transactionTable.getColumns().add(components.PC_IDCol);
		components.transactionTable.getColumns().add(components.CustomerNameCol);
		components.transactionTable.getColumns().add(components.BookedTimeCol);
		
		components.TransactionIDCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
		components.PC_IDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		components.CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
		components.BookedTimeCol.setCellValueFactory(new PropertyValueFactory<>("BookedTime"));
	}
	
	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewCustomerTransactionVar components) {
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.TransactionIDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(1));
		components.PC_IDCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(1));
		components.CustomerNameCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(3));
		components.BookedTimeCol.prefWidthProperty().bind(components.transactionTable.widthProperty().divide(8).multiply(3));
		
		BorderPane.setAlignment(components.titleLbl, Pos.CENTER);
		BorderPane.setAlignment(components.sp, Pos.CENTER);
		
		BorderPane.setMargin(components.titleLbl, new Insets(20));
		components.sp.setPadding(new Insets(20));
	}
	
	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(String role, User user) {
		ViewCustomerTransactionVar components = new ViewCustomerTransactionVar();
		
		components.customer = user;
		
		initialize(components);
		setStyle(components);
		
		MenuController menuController = MenuController.getInstance();
		menuController.selectMenuType(components.bp, user.getUserRole());
		
		return components.scene;
	}
}
