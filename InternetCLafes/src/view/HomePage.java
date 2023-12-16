package view;

import controller.MenuController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.User;

public class HomePage {
	// Home Page akan diakses setelah user login
	// Isinya kosong selain menu bar yang ditentukan dari user role
	
	public class HomeVar{
		public User user;
		Scene scene;
		public BorderPane bp;
	}
	
	public Scene initPage(User user) {
		HomeVar components = new HomeVar();
		initialize(components);
		
		components.user = user;
		
		MenuController menuControl = MenuController.getInstance();
		menuControl.selectMenuType(components.bp, user.getUserRole());

		return components.scene;
	}

	private void initialize(HomeVar components) {
		components.bp = new BorderPane();
		components.scene = new Scene(components.bp, 600, 600);
		
	}
}
