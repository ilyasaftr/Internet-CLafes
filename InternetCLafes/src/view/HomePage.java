package view;

import controller.MenuController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class HomePage {
	// Home Page akan diakses setelah user login
	// Isinya kosong selain menu bar yang ditentukan dari user role
	
	public class HomeVar{
		Scene scene;
		public BorderPane bp;
	}
	
	public Scene initPage(String role) {
		HomeVar components = new HomeVar();
		initialize(components);
		
		MenuController menuControl = MenuController.getInstance();
		menuControl.selectMenuType(components.bp, role);

		return components.scene;
	}

	private void initialize(HomeVar components) {
		components.bp = new BorderPane();
		components.scene = new Scene(components.bp, 600, 600);
		
	}
}
