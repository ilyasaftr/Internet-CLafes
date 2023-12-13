package controller;

import main.Main;
import view.Login;
import view.Login.LoginVar;
import view.Register;
import view.Register.RegisterVar;

public class PageController {
	// Class ini digunakan untuk mengendalikan logic dari page yang akan dituju.

	// PageController memastikan bahwa instance dirinya yang digunakan hanya satu saja melalui Singleton.

	public static volatile PageController instance;

	private PageController() {
		
	}

	public static PageController getInstance() {
		if(instance == null) {
			synchronized (PageController.class) {
				if(instance == null) {
					 instance = new PageController();
				}
			}
		}

		return instance;
	}
	
	public void changePageToLogin(RegisterVar rv) {
		rv.btnRedirectLogin.setOnAction(e -> {
			Main.changeScene(new Login().initPage());
		});
	}

	public void changePageToRegister(LoginVar lv) {
		lv.btnRedirectRegister.setOnAction(e -> {
			Main.changeScene(new Register().initPage());
		});
	}
	
	public void redirectToLogin() {
		Main.changeScene(new Login().initPage());
	}
}
