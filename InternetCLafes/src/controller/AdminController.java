package controller;

import main.Main;
import model.PCBook;
import view.MenuAdmin;
import view.ViewAllPC;
import view.ViewAllReport;
import view.ViewAllStaff;
import view.ViewAllJob;
import view.ViewAllJob.ViewAllJobVar;
import view.ViewAllTransaction;
import view.ViewAssignUser;
import view.ViewJobDetail;
import view.ViewPCDetail;
import view.ViewTransactionDetail;
import view.ViewAllPC.ViewAllPCVar;

public class AdminController {
	// Class ini dianggap sebagai Admin Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Admin
	public MenuAdmin menuAdmin;
	
	// Role Admin
	private final String role = "Admin";
	
	// Admin Controller menggunakan Singleton pattern supaya hanya ada satu instance yang dibuat dalam program
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
		// View All PC
		ma.menuItemViewAllPC.setOnAction(e -> {
			Main.changeScene(new ViewAllPC().initPageAdmin(role));
		});
		// View All Report
		ma.menuItemViewAllReport.setOnAction(e -> {
			Main.changeScene(new ViewAllReport().initPage(role));
		});
		// View All Staff
		ma.menuItemViewAllStaff.setOnAction(e -> {
			Main.changeScene(new ViewAllStaff().initPage(role));
		});
		// View All Job
		ma.menuItemViewAllJob.setOnAction(e -> {
			Main.changeScene(new ViewAllJob().initPage(role));
		});
		// View All Transaction
		ma.menuItemViewAllTransaction.setOnAction(e -> {
			Main.changeScene(new ViewAllTransaction().initPage(role));
		});
	}
	
	// Membuat window baru khusus buat transaction detail
	public void createTransactionDetailWindow(Integer transID) {
		new ViewTransactionDetail().initPage(role, transID);
	}
	
	// Membuat window baru khusus buat job detail
	public void createJobDetailWindow(Integer jobID, ViewAllJobVar components) {
		new ViewJobDetail().initPage(role, jobID, components);
	}
	
	// Membuat window baru khusus buat pc detail
	public void createPCDetailWindow(Integer pcID, ViewAllPCVar components) {
		new ViewPCDetail().initPage(role, pcID, components);
	}

	// Membuat window baru khusus assign user di fitur add job
	public void createAssignUser(PCBook pcBook, Integer pcId) {
		new ViewAssignUser().initPage(pcBook, pcId);
	}
	
}
