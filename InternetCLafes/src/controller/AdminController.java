package controller;

import main.Main;
import view.MenuAdmin;
import view.ViewAllPC;
import view.ViewAllReport;
import view.ViewAllStaff;
import view.ViewAllTechnician;
import view.ViewAllTransaction;
import view.ViewPCDetail;
import view.ViewTransactionDetail;
import view.ViewAllPC.ViewAllPCVar;

public class AdminController {
	// Class ini dianggap sebagai Admin Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Admin
	public MenuAdmin menuAdmin;
	
	// Role Admin
	private final String role = "Admin";
	
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
		menuAdmin = new MenuAdmin();
		menuAdmin.initialize();
		addHandlers(menuAdmin);
	}

	// Menambahkan event handler onclick untuk tiap menu item
	private void addHandlers(MenuAdmin ma) {
		ma.menuItemViewAllPC.setOnAction(e -> {
			Main.changeScene(new ViewAllPC().initPageAdmin(role));
		});
		ma.menuItemViewAllReport.setOnAction(e -> {
			Main.changeScene(new ViewAllReport().initPage(role));
		});
		ma.menuItemViewAllStaff.setOnAction(e -> {
			Main.changeScene(new ViewAllStaff().initPage(role));
		});
		ma.menuItemViewAllTechJob.setOnAction(e -> {
			Main.changeScene(new ViewAllTechnician().initPage(role));
		});
		ma.menuItemViewAllTransaction.setOnAction(e -> {
			Main.changeScene(new ViewAllTransaction().initPage(role));
		});
	}
	
	// Membuat window baru khusus transaction detail
	public void createTransactionDetailWindow(Integer transID) {
		new ViewTransactionDetail().initPage(role, transID);
	}
	
	// Membuat window baru khusus pc detail
	public void createPCDetailWindow(Integer pcID, ViewAllPCVar components) {
		new ViewPCDetail().initPage(role, pcID, components);
	}
	
}
