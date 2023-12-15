package view;

import controller.PCController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PC;
import view.ViewAllPC.ViewAllPCVar;

public class ViewPCDetail {
	/*
	 * ViewPCDetailVar berisi semua komponen UI yang digunakan di page ViewPCDetail
	 */
	public class ViewPCDetailVar {
		public Stage stage;
		Scene scene;

		VBox vb;
		GridPane gp;
		HBox hb;

		Label titleLbl, pcIdLbl, pcCondLbl;
		public TextField pcIdTf;
		public ComboBox<String> pcCondTf;
		
		public Button btnUpdate, btnDelete;
		public Alert alert;

		PC pc;
	}
	
	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewPCDetailVar components) {

		components.titleLbl = new Label("View PC Details");

		components.pcIdLbl = new Label("PC ID:");
		components.pcCondLbl = new Label("PC Condition:");
		
		components.pcIdTf = new TextField();
		components.pcCondTf = new ComboBox<>();
		components.pcCondTf.getItems().addAll("Usable", "Maintenance", "Broken");
		
		components.gp = new GridPane();
		components.gp.add(components.pcIdLbl, 0, 0);
		components.gp.add(components.pcCondLbl, 0, 1);
		components.gp.add(components.pcIdTf, 1, 0);
		components.gp.add(components.pcCondTf, 1, 1);
		
		components.btnUpdate = new Button("Update");
		components.btnDelete = new Button("Delete");
		components.hb = new HBox();
		components.hb.getChildren().addAll(components.btnUpdate, components.btnDelete);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.titleLbl, components.gp, components.hb);

		components.stage = new Stage();
		components.scene = new Scene(components.vb);
		components.stage.setScene(components.scene);
	}

	// getdata untuk mendapatkan data dari tabel
	private void getData(Integer pcID, ViewPCDetailVar components) {
		PCController pcCont = PCController.getInstance();
		components.pc = pcCont.getPCDetail(pcID);
		
		components.pcIdTf.setText(components.pc.getPC_ID().toString());
		components.pcCondTf.getSelectionModel().select(components.pc.getPC_Condition());
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewPCDetailVar components) {
		components.gp.setVgap(15);
		components.gp.setHgap(15);
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
		components.pcIdTf.setEditable(false);
		components.pcIdTf.setStyle("-fx-background-color: #d3d3d3;");
		
		components.hb.setSpacing(30);
	}
	
	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public void initPage(String role, Integer pcId, ViewAllPCVar viewAllPCVar) {
		PCController pcCont = PCController.getInstance();
		ViewPCDetailVar components = new ViewPCDetailVar();
		
		initialize(components);
		setStyle(components);
		getData(pcId, components);
		initializeAlert(components);
		
		pcCont.addUpdatePCHandler(components, viewAllPCVar);
		pcCont.addDeletePCHandler(components, viewAllPCVar);
		
		components.stage.show();
	}
	
	private void initializeAlert(ViewPCDetailVar components) {
		components.alert = new Alert(AlertType.ERROR);
		components.alert.setTitle("Error");
	}
}
