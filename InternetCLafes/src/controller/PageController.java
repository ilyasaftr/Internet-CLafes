package controller;

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
