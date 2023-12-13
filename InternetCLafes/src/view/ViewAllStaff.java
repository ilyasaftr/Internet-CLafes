package view;

import java.util.Vector;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.User;

public class ViewAllStaff {
	/*
	 * ViewAllStaffVar berisi semua komponen UI yang digunakan di page ViewAllStaff
	 */
	public class ViewAllStaffVar{
		public Vector<User> userList;
		
		public Stage stage;
		Scene scene;

		BorderPane bp;

		Label titleLbl, userIDLbl, userRoleLbl;
		public TextField userIDTf;
		public ComboBox<String> userRoleCB;
		
		GridPane gp;
		
		public Button btnChangeRole;
		
		ScrollPane sp;
		
		VBox vb;
		
		public Alert noRowAlert, invalidDataAlert;
		
		public TableView<User> staffTable;
		TableColumn<User, Integer> userIDCol, userAgeCol;
		TableColumn<User, String> userRoleCol, userNameCol, userPasswordCol;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewAllStaffVar components) {

		components.titleLbl = new Label("View All Staff");
		components.userIDLbl = new Label("User ID: ");
		components.userRoleLbl = new Label("User Role: ");
		
		components.userIDTf = new TextField();
		components.userRoleCB = new ComboBox<String>();
		components.userRoleCB.getItems().add("Admin");
		components.userRoleCB.getItems().add("Computer Technician");
		components.userRoleCB.getItems().add("Customer");
		components.userRoleCB.getItems().add("Operator");

		initializeTable(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.staffTable);
		components.sp.setFitToWidth(true);

		components.btnChangeRole = new Button("Change Role");
		
		components.gp = new GridPane();
		components.gp.add(components.userIDLbl, 0, 0);
		components.gp.add(components.userIDTf, 1, 0);
		components.gp.add(components.userRoleLbl, 0, 1);
		components.gp.add(components.userRoleCB, 1, 1);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.sp, components.gp, components.btnChangeRole);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);

		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewAllStaffVar components) {
		
		// atur ukuran lebar setiap kolom menggunakan dynamic binding
		components.userIDCol.prefWidthProperty().bind(components.staffTable.widthProperty().divide(10).multiply(2));
		components.userNameCol.prefWidthProperty().bind(components.staffTable.widthProperty().divide(10).multiply(2));
		components.userPasswordCol.prefWidthProperty().bind(components.staffTable.widthProperty().divide(10).multiply(2));
		components.userAgeCol.prefWidthProperty().bind(components.staffTable.widthProperty().divide(10).multiply(2));
		components.userRoleCol.prefWidthProperty().bind(components.staffTable.widthProperty().divide(10).multiply(2));
		
		components.userIDTf.setEditable(false);
		components.userIDTf.setStyle("-fx-background-color: #d3d3d3;");
		
		components.gp.setHgap(10);
		components.gp.setVgap(10);
		
		components.vb.setSpacing(30);
		components.bp.setPadding(new Insets(10));
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel dan kolom-kolomnya juga
	 */

	private void initializeTable(ViewAllStaffVar components) {
		components.staffTable = new TableView<>();

		components.userPasswordCol = new TableColumn<>("User Password");
		components.userNameCol = new TableColumn<>("User Name");
		components.userAgeCol = new TableColumn<>("User Age");
		components.userRoleCol = new TableColumn<>("User Role");
		components.userIDCol = new TableColumn<>("User ID");

		components.staffTable.getColumns().add(components.userIDCol);
		components.staffTable.getColumns().add(components.userNameCol);
		components.staffTable.getColumns().add(components.userPasswordCol);
		components.staffTable.getColumns().add(components.userAgeCol);
		components.staffTable.getColumns().add(components.userRoleCol);

		components.userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
		components.userNameCol.setCellValueFactory(new PropertyValueFactory<>("UserName"));
		components.userPasswordCol.setCellValueFactory(new PropertyValueFactory<>("UserPassword"));
		components.userAgeCol.setCellValueFactory(new PropertyValueFactory<>("UserAge"));
		components.userRoleCol.setCellValueFactory(new PropertyValueFactory<>("UserRole"));
		
		// membuat agar kolom bisa text wrap
		components.userNameCol.setCellFactory(new Callback<TableColumn<User,String>, TableCell<User,String>>() {
			
			@Override
			public TableCell<User, String> call(TableColumn<User, String> param) {
				TableCell<User, String> cell = new TableCell<>();
			    Text text = new Text();
			    cell.setGraphic(text);
			    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			    text.wrappingWidthProperty().bind(components.userNameCol.widthProperty());
			    text.textProperty().bind(cell.itemProperty());
			    return cell;
			}
		});
		
		// membuat agar userRoleCol bisa text wrap
		components.userPasswordCol.setCellFactory(new Callback<TableColumn<User,String>, TableCell<User,String>>() {
			
			@Override
			public TableCell<User, String> call(TableColumn<User, String> param) {
				TableCell<User, String> cell = new TableCell<>();
			    Text text = new Text();
			    cell.setGraphic(text);
			    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			    text.wrappingWidthProperty().bind(components.userPasswordCol.widthProperty());
			    text.textProperty().bind(cell.itemProperty());
			    return cell;
			}
		});
		
		UserController uc = UserController.getInstance();
		uc.getData(components);
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage() {
		ViewAllStaffVar components = new ViewAllStaffVar();
		initialize(components);
		initializeAlert(components);
		
		setStyle(components);
		
		UserController uc = UserController.getInstance();
		uc.addChangeRoleHandler(components);

		return components.scene;
	}
	
	public void initializeAlert(ViewAllStaffVar components) {
		components.noRowAlert = new Alert(AlertType.ERROR);
		components.noRowAlert.setTitle("Error");
		components.noRowAlert.setContentText("You must select a row from the table!");
		
		components.invalidDataAlert = new Alert(AlertType.ERROR);
		components.invalidDataAlert.setTitle("Error");
		components.invalidDataAlert.setContentText("Invalid data!");
	}

}
