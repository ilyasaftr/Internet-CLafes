package view;


import controller.MenuController;
import controller.PCController;
import controller.ReportController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MakeReport {
	public class MakeReportVar{
		Scene scene;

		BorderPane bp;
		VBox vb;

		Label titleLbl, PC_IDLbl, ReportNoteLbl;
		public ComboBox<Integer> PC_IDCB;
		public TextArea ReportNoteTxt;
		
		public Button btnSubmit;

		GridPane gp;
		
		public Alert errorAlert, successAlert;
	}
	
	private void initialize(MakeReportVar components) {

		PCController pc = PCController.getInstance();
		
		components.titleLbl = new Label("Make Report");
		
		components.PC_IDLbl = new Label("PC ID:");
		components.ReportNoteLbl = new Label("Report Note:");
		
		components.PC_IDCB = new ComboBox<>();
		components.PC_IDCB.getItems().addAll(pc.getAllPCID());
		
		components.ReportNoteTxt = new TextArea();
		
		components.btnSubmit = new Button("Submit");
		
		components.gp = new GridPane();
		components.gp.add(components.PC_IDLbl, 0, 0);
		components.gp.add(components.ReportNoteLbl, 0, 1);
		
		components.gp.add(components.PC_IDCB, 1, 0);
		components.gp.add(components.ReportNoteTxt, 1, 1);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.gp, components.btnSubmit);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);

		components.scene = new Scene(components.bp);
	}
	
	private void setStyle(MakeReportVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		components.gp.setVgap(10);
		components.gp.setHgap(15);
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
	}
	
	public Scene initPage(String role) {
		MenuController mc = MenuController.getInstance();
		ReportController rc = ReportController.getInstance();
		
		MakeReportVar components = new MakeReportVar();
		initialize(components);
		setStyle(components);
		initializeAlert(components);
		mc.selectMenuType(components.bp, role);
		
		rc.addAddReportHandler(components, role);
		
		return components.scene;
	}

	private void initializeAlert(MakeReportVar components) {
		components.errorAlert = new Alert(AlertType.ERROR);
		components.errorAlert.setTitle("Error");
		
		components.successAlert = new Alert(AlertType.INFORMATION);
		components.successAlert.setTitle("Success");
	}
}
