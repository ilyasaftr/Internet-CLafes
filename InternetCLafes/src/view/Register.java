package view;

import controller.PageController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Register {
	// Page untuk melakukan register bagi user
	// Landing page dari seluruh app
	
	public class RegisterVar{
		Scene scene;
		
		BorderPane bp;
		GridPane gp;
		
		HBox hb;
		
		public TextField UserNameTf;
		public PasswordField PasswordTf, ConfirmPasswordTf;
		public Spinner<Integer> UserAgeSpinner;
		Label TitleLbl, UserNameLbl, PasswordLbl, ConfirmPasswordLbl, UserAgeLbl;

		public Button btnRegister, btnRedirectLogin;
		
		public Alert alert;
	}
	
	// Init UI app
	private void initialize(RegisterVar rv) {
		rv.bp = new BorderPane();
		rv.gp = new GridPane();
		rv.hb = new HBox();
		
		rv.TitleLbl = new Label("Register");
		rv.UserNameLbl = new Label("User Name: ");
		rv.PasswordLbl = new Label("Password: ");
		rv.ConfirmPasswordLbl = new Label("Confirm Password: ");
		rv.UserAgeLbl = new Label("User Age: ");
		
		rv.UserNameTf = new TextField();
		rv.PasswordTf = new PasswordField();
		rv.ConfirmPasswordTf = new PasswordField();
		rv.UserAgeSpinner = new Spinner<Integer>(13, 65, 13);
		
		rv.bp.setTop(rv.TitleLbl);
		
		rv.gp.add(rv.UserNameLbl, 0, 0);
		rv.gp.add(rv.PasswordLbl, 0, 1);
		rv.gp.add(rv.ConfirmPasswordLbl, 0, 2);
		rv.gp.add(rv.UserAgeLbl, 0, 3);
		
		rv.gp.add(rv.UserNameTf, 1, 0);
		rv.gp.add(rv.PasswordTf, 1, 1);
		rv.gp.add(rv.ConfirmPasswordTf,1, 2);
		rv.gp.add(rv.UserAgeSpinner, 1, 3);
		
		rv.bp.setCenter(rv.gp);
		
		rv.btnRegister = new Button("Register");
		rv.btnRedirectLogin = new Button("Redirect to Login");
		rv.hb.getChildren().addAll(rv.btnRegister, rv.btnRedirectLogin);
		
		rv.bp.setBottom(rv.hb);
		
		rv.scene = new Scene(rv.bp);
	}
	
	// atur styling dari page
	private void setStyle(RegisterVar rv) {
		BorderPane.setAlignment(rv.TitleLbl, Pos.CENTER);
		BorderPane.setMargin(rv.TitleLbl, new Insets(30, 0, 60, 0));
		BorderPane.setAlignment(rv.gp, Pos.CENTER);
		rv.gp.setPadding(new Insets(20));
		rv.gp.setHgap(20);
		rv.gp.setVgap(40);
		rv.gp.setAlignment(Pos.CENTER);
		rv.UserAgeSpinner.setEditable(true);
		BorderPane.setAlignment(rv.hb, Pos.CENTER);
		rv.hb.setPadding(new Insets(20));
		rv.hb.setAlignment(Pos.CENTER);
		rv.hb.setSpacing(20);
	}
	
	// init alert error yang muncul nantinya
	private void initAlert(RegisterVar rv) {
		rv.alert = new Alert(AlertType.ERROR);
		rv.alert.titleProperty().set("Error");
	}
	
	// atur event handler dari register page
	private void addHandlers(RegisterVar rv) {
		// atur logika register
		UserController uc = UserController.getInstance();
		uc.addRegisterHandlers(rv);
		
		// atur logika redirect ke login page
		PageController pc = PageController.getInstance();
		pc.changePageToLogin(rv);
	}
	
	public Scene initPage() {
		RegisterVar components = new RegisterVar();
		initialize(components);
		initAlert(components);
		setStyle(components);
		addHandlers(components);

		return components.scene;
	}
}
