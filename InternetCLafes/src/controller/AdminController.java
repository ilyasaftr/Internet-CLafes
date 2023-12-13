package controller;

import view.MenuAdmin;

public class AdminController {
	public MenuAdmin menuAdmin;
	
	public static volatile AdminController instance = null;
	
	public static AdminController getInstance() {
		if(instance == null) {
			synchronized (AdminController.class) {
				if(instance == null) {
					instance = new AdminController();
				}
			}
		}
		
		return instance;
	}
	
	private AdminController() {
		MenuAdmin = 
	}
}
