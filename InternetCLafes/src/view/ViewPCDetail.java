package view;

import java.time.LocalTime;
import java.util.Vector;

import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;

public class ViewPCDetail {
	/*
	 * ViewPCDetailVar berisi semua komponen UI yang digunakan di page ViewPCDetail
	 */
	public class ViewPCDetailVar {
		public Stage stage;
		Scene scene;

		VBox vb;
		GridPane gp;

		Label titleLbl, pcIdLbl, pcCondLbl;
		TextField pcIdTf;
		ComboBox<String> pcCondTf;

		PC pc;
	}
	
	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewPCDetailVar components) {

		components.titleLbl = new Label("View PC Details");

		initializeTable(components);
		getData(components);
		
		components.pcIdLbl = new Label("PC ID:");
		components.pcCondLbl = new Label("PC Condition:");
		
		components.pcIdTf = new TextField();
		components.pcCondTf = new ComboBox<>();
		components.pcCondTf.getItems().addAll("Usable", "Maintenance", "");
		
		components.gp = new GridPane();
		components.gp.add(components.pcIdLbl, 0, 0);
		components.gp.add(components.pcCondLbl, 0, 1);
		components.gp.add(components.pcIdTf, 1, 0);
		components.gp.add(components.pcCondTf, 1, 1);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.titleLbl, components.gp);

		components.stage = new Stage();
		components.scene = new Scene(components.vb);
		components.stage.setScene(components.scene);
	}

	// getdata untuk mendapatkan data dari tabel
	private void getData(ViewPCDetailVar components) {
		TransactionController transControl = TransactionController.getInstance();
		Vector<TransactionDetail> transList = transControl.getAllTransactionDetail(components.transactionID);

		for(TransactionDetail trans : transList) {
			components.transactionTable.getItems().add(trans);
		}
	}

	// initializetable untuk membuat tabel dan kolom-kolomnya serta data yang diproses dalamnya
	private void initializeTable(ViewPCDetailVar components) {
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
	private void setStyle(ViewPCDetailVar components) {
		components.gp.setVgap(15);
		components.gp.setHgap(15);
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
	}
	
	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public void initPage(String role, Integer transID) {
		ViewPCDetailVar components = new ViewPCDetailVar();
		
		components.transactionID = transID;
		
		initialize(components);
		setStyle(components);
		
		components.stage = new Stage();
		components.stage.setScene(components.scene);
		components.stage.show();
	}
}
