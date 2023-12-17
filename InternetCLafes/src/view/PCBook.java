package view;


import java.time.LocalDate;

import controller.MenuController;
import controller.PCBookController;
import controller.PCController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.User;

public class PCBook {
	public class PCBookVar{
		public User user;
		Scene scene;

		BorderPane bp;
		VBox vb;

		Label titleLbl, PC_IDLbl, BookDateLbl;
		public ComboBox<Integer> PC_IDCB;
		public DatePicker BookDateDP;
		
		public Button btnSubmit;

		GridPane gp;
		
		public Alert errorAlert, successAlert;
	}
	
	private void initialize(PCBookVar components) {

		PCController pc = PCController.getInstance();
		
		components.titleLbl = new Label("Book PC");
		
		components.PC_IDLbl = new Label("PC ID:");
		components.BookDateLbl = new Label("Book Date:");
		
		components.PC_IDCB = new ComboBox<>();
		components.PC_IDCB.getItems().addAll(pc.getAllPCID());
		
		components.BookDateDP = new DatePicker();
		components.BookDateDP.setValue(LocalDate.now());

		
		components.btnSubmit = new Button("Submit");
		
		components.gp = new GridPane();
		components.gp.add(components.PC_IDLbl, 0, 0);
		components.gp.add(components.BookDateLbl, 0, 1);
		
		components.gp.add(components.PC_IDCB, 1, 0);
		components.gp.add(components.BookDateDP, 1, 1);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.gp, components.btnSubmit);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);

		components.scene = new Scene(components.bp);
	}
	
	private void setStyle(PCBookVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		components.gp.setVgap(10);
		components.gp.setHgap(15);
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
	}
	
	public Scene initPage(User user) {
		MenuController mc = MenuController.getInstance();
		PCBookController pc = PCBookController.getInstance();
		
		PCBookVar components = new PCBookVar();
		initialize(components);
		setStyle(components);
		initializeAlert(components);
		mc.selectMenuType(components.bp, user.getUserRole());
		pc.addPCBookHandler(components, user.getUserID());	
		
		return components.scene;
	}

	private void initializeAlert(PCBookVar components) {
		components.errorAlert = new Alert(AlertType.ERROR);
		components.errorAlert.setTitle("Error");
		
		components.successAlert = new Alert(AlertType.INFORMATION);
		components.successAlert.setTitle("Success");
	}
}
