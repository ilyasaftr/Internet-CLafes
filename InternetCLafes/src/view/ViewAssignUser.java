package view;

import controller.PCBookController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PCBook;

public class ViewAssignUser {

	public class ViewAssignUserVar {
		public Stage stage;
		Scene scene;

		VBox vb;
		GridPane gp;

		Label titleLbl, bookIDLbl, newPCIDLbl;
		public TextField bookIDTf, newPCIDTf;
		
		public Button btnAssign;
		public Alert alert;

		Integer pcId;
		public PCBook pcBook;
	}
	
	private void initialize(ViewAssignUserVar components) {

		components.titleLbl = new Label(String.format("Assign User - User ID: %d", components.pcBook.getUserID()));

		components.bookIDLbl = new Label("Book ID:");
		components.newPCIDLbl = new Label("New PC ID:");
		
		components.bookIDTf = new TextField();
		components.newPCIDTf = new TextField();
		
		components.gp = new GridPane();
		components.gp.add(components.bookIDLbl, 0, 0);
		components.gp.add(components.newPCIDLbl, 0, 1);
		components.gp.add(components.bookIDTf, 1, 0);
		components.gp.add(components.newPCIDTf, 1, 1);
		
		components.btnAssign = new Button("Save");
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.titleLbl, components.gp, components.btnAssign);

		components.stage = new Stage();
		components.scene = new Scene(components.vb);
		components.stage.setScene(components.scene);
	}

	private void getData(ViewAssignUserVar components) {
		components.bookIDTf.setText(components.pcBook.getBook_ID().toString());
	}

	private void setStyle(ViewAssignUserVar components) {
		components.gp.setVgap(15);
		components.gp.setHgap(15);
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
		components.bookIDTf.setEditable(false);
		components.bookIDTf.setStyle("-fx-background-color: #d3d3d3;");
		
	}
	
	public void initPage(PCBook pcBook, Integer pcId) {
		PCBookController pcBookControl = PCBookController.getInstance();
		ViewAssignUserVar components = new ViewAssignUserVar();
		
		components.pcBook = pcBook;
		components.pcId = pcId;
		
		initialize(components);
		setStyle(components);
		getData(components);
		initializeAlert(components);
		
		pcBookControl.addAssignPcHandler(components);
		
		components.stage.showAndWait();
	}
	
	private void initializeAlert(ViewAssignUserVar components) {
		components.alert = new Alert(AlertType.ERROR);
		components.alert.setTitle("Error");
	}

}
