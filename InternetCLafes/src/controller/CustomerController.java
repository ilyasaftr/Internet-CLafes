package controller;

import main.Main;
import view.MenuCustomer;

public class CustomerController {
	// Class ini dianggap sebagai Customer Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Customer
	
	public MenuCustomer menuCustomer;
	private final String role = "Customer";
	
	public static volatile CustomerController instance = null;
	
	public static CustomerController getInstance() {
		if(instance == null) {
			synchronized (CustomerController.class) {
				if(instance == null) {
					instance = new CustomerController();
				}
			}
		}
		
		return instance;
	}
	
	private CustomerController() {
		menuCustomer = new MenuCustomer();
		menuCustomer.initialize();
		addHandlers(menuCustomer);
	}

	private void addHandlers(MenuCustomer mc) {
		mc.menuItemBookPC.setOnAction(e->{
			Main.changeScene(null);
		});
		mc.menuItemMakeReport.setOnAction(e->{
			Main.changeScene(null);
		});
		mc.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(null);
		});
		mc.menuItemViewCustomerTransaction.setOnAction(e->{
			Main.changeScene(null);
		});
	}
}
