package view;

import java.util.Vector;

import controller.AdminController;
import controller.MenuController;
import controller.PCController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.PC;
import view.ViewAllPC.ViewAllPCVar;

public class ViewAllTechnician {
	/*
	 * ViewAllPCVar berisi semua komponen UI yang digunakan di page ViewAllPC
	 */
	public class ViewAllPCVar{
		Scene scene;

		BorderPane bp;

		VBox vb;
		Label titleLbl, pcIdLbl;
		public TextField pcIdTf;
		
		public Button btnCreatePC;

		ScrollPane sp;
		GridPane gp;
		
		public TableView<PC> pcTable;
		TableColumn<PC, Integer> PC_IDCol;
		TableColumn<PC, String> PC_ConditionCol;
		
		public Alert alert;
		
		public Vector<PC> pcList;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewAllPCVar components) {

		components.titleLbl = new Label("View All PCs");

		initializeTable(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.pcTable);
		components.sp.setFitToWidth(true);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.sp);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);

		components.scene = new Scene(components.bp);
	}
	
	private void initializeAdmin(ViewAllPCVar components) {

		components.btnCreatePC = new Button("Add PC");
		
		components.pcIdLbl = new Label("PC ID:");
		
		components.pcIdTf = new TextField();
		
		components.gp = new GridPane();
		components.gp.add(components.pcIdLbl, 0, 0);
		components.gp.add(components.pcIdTf, 1, 0);
		
		components.vb.getChildren().addAll(components.gp, components.btnCreatePC);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewAllPCVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.PC_IDCol.prefWidthProperty().bind(components.pcTable.widthProperty().divide(2).multiply(1));
		components.PC_ConditionCol.prefWidthProperty().bind(components.pcTable.widthProperty().divide(2).multiply(1));
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
	}

	private void setStyleAdmin(ViewAllPCVar components) {
		components.gp.setVgap(10);
		components.gp.setHgap(15);
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
	public Scene initPageAdmin(String role) {
		PCController pcCont = PCController.getInstance();
		MenuController mc = MenuController.getInstance();
		
		ViewAllPCVar components = new ViewAllPCVar();
		initialize(components);
		initializeAdmin(components);
		pcCont.getData(components);
		setStyle(components);
		setStyleAdmin(components);
		initializeAlert(components);
		mc.selectMenuType(components.bp, role);
		
		pcCont.addViewPCDetailHandler(components);
		pcCont.addCreatePCHandler(components);

		return components.scene;
	}
	
	public Scene initPage(String role) {
		PCController pcCont = PCController.getInstance();
		MenuController mc = MenuController.getInstance();
		
		ViewAllPCVar components = new ViewAllPCVar();
		initialize(components);
		pcCont.getData(components);
		setStyle(components);
		initializeAlert(components);
		mc.selectMenuType(components.bp, role);
		
		return components.scene;
	}

	private void initializeAlert(ViewAllPCVar components) {
		components.alert = new Alert(AlertType.ERROR);
		components.alert.setTitle("Error");
	}
}
