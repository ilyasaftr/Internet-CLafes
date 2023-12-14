package view;

import controller.PageController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Login {
	// Halaman untuk user melakukan login
	public class LoginVar{
		Scene scene;
		
		BorderPane bp;
		GridPane gp;
		
		HBox hb;
		
		public TextField UserNameTf;
		public PasswordField PasswordTf;
		Label TitleLbl, UserNameLbl, PasswordLbl;

		public Button btnLogin, btnRedirectRegister;
		
		public Alert alert;
	}
	
	// init semua komponen UI
	private void initialize(LoginVar lv) {
		lv.bp = new BorderPane();
		lv.gp = new GridPane();
		lv.hb = new HBox();
		
		lv.TitleLbl = new Label("Login");
		lv.UserNameLbl = new Label("User Name: ");
		lv.PasswordLbl = new Label("Password: ");
		
		lv.UserNameTf = new TextField();
		lv.PasswordTf = new PasswordField();
		
		lv.bp.setTop(lv.TitleLbl);
		
		lv.gp.add(lv.UserNameLbl, 0, 0);
		lv.gp.add(lv.PasswordLbl, 0, 1);
		
		lv.gp.add(lv.UserNameTf, 1, 0);
		lv.gp.add(lv.PasswordTf, 1, 1);
		
		lv.bp.setCenter(lv.gp);
		
		lv.btnLogin = new Button("Login");
		lv.btnRedirectRegister = new Button("Redirect to Register");
		lv.hb.getChildren().addAll(lv.btnLogin, lv.btnRedirectRegister);
		
		lv.bp.setBottom(lv.hb);
		
		lv.scene = new Scene(lv.bp);
	}
	
	private void setStyle(LoginVar lv) {
		BorderPane.setAlignment(lv.TitleLbl, Pos.CENTER);
		BorderPane.setMargin(lv.TitleLbl, new Insets(30, 0, 60, 0));
		BorderPane.setAlignment(lv.gp, Pos.CENTER);
		lv.gp.setPadding(new Insets(20));
		lv.gp.setHgap(20);
		lv.gp.setVgap(40);
		BorderPane.setAlignment(lv.hb, Pos.CENTER);
		lv.hb.setPadding(new Insets(20));
		lv.hb.setAlignment(Pos.CENTER);
		lv.hb.setSpacing(20);
	}
	
	// init alert error
	private void initAlert(LoginVar lv) {
		lv.alert = new Alert(AlertType.ERROR);
		lv.alert.titleProperty().set("Error");
	}
	
	// init handler event
	private void addHandlers(LoginVar lv) {
		// atur logika login
		UserController uc = UserController.getInstance();
		uc.addLoginHandlers(lv);
		
		// atur logika tombol redirect register
		PageController pc = PageController.getInstance();
		pc.changePageToRegister(lv);
	}

	public Scene initPage() {
		LoginVar components = new LoginVar();
		initialize(components);
		initAlert(components);
		setStyle(components);
		addHandlers(components);

		return components.scene;
	}
}
