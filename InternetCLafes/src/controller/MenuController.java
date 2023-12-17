package controller;

import javafx.scene.layout.BorderPane;

public class MenuController {
	// Menu Controller menggunakan Singleton untuk memastikan hanya ada satu instance.
	// Menu Controller digunakan untuk menentukan jenis menu apa yang akan diinisialisasikan untuk masing-masing page yang ada berdasarkan role yang terpilih.
	
	public static volatile MenuController instance;

	private MenuController() {
		
	}

	public static MenuController getInstance() {
		if(instance == null) {
			synchronized (MenuController.class) {
				if(instance == null) {
					 instance = new MenuController();
				}
			}
		}

		return instance;
	}
	
	// menyeleksi dan attach menu ke BorderPane sebuah view berdasarkan role
	public void selectMenuType(BorderPane bp, String role) {
		if(role.equals("Admin")) {
			bp.setTop(AdminController.getInstance().menuAdmin.menuBar);
		}
		else if(role.equals("Operator")) {
			bp.setTop(OperatorController.getInstance().menuOperator.menuBar);
		}
		else if(role.equals("Computer Technician")) {
			bp.setTop(ComputerTechnicianController.getInstance().menuTechnician.menuBar);
		}
		else if(role.equals("Customer")) {
			bp.setTop(CustomerController.getInstance().menuCustomer.menuBar);
		}
	}
}
