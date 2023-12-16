package controller;

import main.Main;
import view.MakeReport;
import view.MenuCustomer;
import view.ViewAllPC;
import view.ViewCustomerTransaction;

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
	// Menambahkan event handler onclick untuk tiap menu item
	private void addHandlers(MenuCustomer mc) {
		mc.menuItemBookPC.setOnAction(e->{
			Main.changeScene(null);
		});
		mc.menuItemMakeReport.setOnAction(e->{
			Main.changeScene(new MakeReport().initPage(role));
		});
		mc.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(new ViewAllPC().initPage(role));
		});
		// menambahkan param user untuk mendapatkan user ID
		mc.menuItemViewCustomerTransaction.setOnAction(e->{
			Main.changeScene(new ViewCustomerTransaction().initPage(role, Main.user));
		});
	}
}
