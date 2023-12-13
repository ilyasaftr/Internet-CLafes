package controller;

import javafx.scene.control.MenuBar;

public class PageController {
	// Class ini digunakan untuk mengendalikan logic dari page yang akan dituju.

	// PageController memastikan bahwa instance dirinya yang digunakan hanya satu saja melalui Singleton.

	public static volatile String role;
	public static volatile PageController instance;

	private PageController(String role) {
		this.role = role;
	}

	public static PageController getInstance(String role) {
		if(instance == null) {
			synchronized (PageController.class) {
				if(instance == null) {
					 instance = new PageController(role);
				}
			}
		}

		return instance;
	}

	public void initMenu(MenuBar menuBar) {
		if(role.equals("Admin")) {
			
		}
		else if(role.equals("Operator")) {
					
				}
		else if(role.equals("Customer")) {
			
		}
		else if(role.equals("Computer Technician")) {
			
		}
	}
//	public void changePageToLogin(MenuItem) {
//		regisVar.button_login.setOnAction(e -> {
//			// logic ganti scene
//			Main.changeScene(new LoginPage().initializeLoginPage());
//		});
//	}
//
//	public void changePageToRegister(LoginVar loginVar) {
//		loginVar.button_regis.setOnAction(e -> {
//			// logic ganti scene
//			Main.changeScene(new RegisterPage().initializeRegisterPage());
//		});
//	}
}
