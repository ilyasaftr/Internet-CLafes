package controller;

import main.Main;
import view.HomePage;
import view.Login;
import view.Login.LoginVar;
import view.Register;
import view.Register.RegisterVar;

public class PageController {
	// Class ini digunakan untuk mengendalikan logic dari page yang akan dituju, tepatnya pada bagian awal saja (login, register).

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
	
	// set event listener untuk register page
	// kalau diclick, ke login page
	public void changePageToLogin(RegisterVar rv) {
		rv.btnRedirectLogin.setOnAction(e -> {
			Main.changeScene(new Login().initPage());
		});
	}
	
	// set event listener untuk login page
	// kalau diclick, ke register page
	public void changePageToRegister(LoginVar lv) {
		lv.btnRedirectRegister.setOnAction(e -> {
			Main.changeScene(new Register().initPage());
		});
	}
	
	// method untuk langsung redirect ke login page, kalau sudah register
	public void redirectToLogin() {
		Main.changeScene(new Login().initPage());
	}
	
	// method untuk langsung redirect ke home page, kalau sudah login
	public void redirectToHome(String role) {
		Main.changeScene(new HomePage().initPage(role));
	}
}
