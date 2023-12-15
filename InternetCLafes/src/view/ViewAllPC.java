package view;

import java.util.Vector;

import controller.AdminController;
import controller.PCController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.PC;

public class ViewAllPC {
	/*
	 * ViewAllPCVar berisi semua komponen UI yang digunakan di page ViewAllPC
	 */
	public class ViewAllPCVar{
		Scene scene;

		BorderPane bp;

		Label titleLbl;

		ScrollPane sp;
		
		public TableView<PC> pcTable;
		TableColumn<PC, Integer> PC_IDCol;
		TableColumn<PC, String> PC_ConditionCol;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewAllPCVar components) {

		components.titleLbl = new Label("View All PCs");

		initializeTable(components);
		getData(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.pcTable);
		components.sp.setFitToWidth(true);

		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.sp);

		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewAllPCVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.PC_IDCol.prefWidthProperty().bind(components.pcTable.widthProperty().divide(2).multiply(1));
		components.PC_ConditionCol.prefWidthProperty().bind(components.pcTable.widthProperty().divide(2).multiply(1));
	}

	/*
	 * getData digunakan untuk mendapatkan data report semua yang akan dimasukkan ke tabel
	 */
	private void getData(ViewAllPCVar components) {
		PCController pcControl = PCController.getInstance();
		Vector<PC> pcList = pcControl.getAllPCData();

		for(PC pc : pcList) {
			components.pcTable.getItems().add(pc);
		}
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel, kolom-kolomnya juga
	 */
	private void initializeTable(ViewAllPCVar components) {
		components.pcTable = new TableView<>();

		components.PC_IDCol = new TableColumn<>("PC ID");
		components.PC_ConditionCol = new TableColumn<>("PC Condition");

		components.pcTable.getColumns().add(components.PC_IDCol);
		components.pcTable.getColumns().add(components.PC_ConditionCol);
		
		components.PC_IDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		components.PC_ConditionCol.setCellValueFactory(new PropertyValueFactory<>("PC_Condition"));
		
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(String role) {
		ViewAllPCVar components = new ViewAllPCVar();
		initialize(components);
		setStyle(components);
		
		components.bp.setTop(AdminController.getInstance().menuAdmin.menuBar);

		return components.scene;
	}
}
