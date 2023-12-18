package controller;

import main.Main;
import model.PCBook;
import view.MakeReport;
import view.MenuOperator;
import view.ViewAllPC;
import view.ViewAssignUser;
import view.ViewPCBooked;
import view.ViewPCBooked.ViewPCBookedVar;

public class OperatorController {
	// Class ini dianggap sebagai Operator Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Operator
	
	public MenuOperator menuOperator;
	private final String role = "Operator";
	
	public static volatile OperatorController instance = null;
	
	public static OperatorController getInstance() {
		if(instance == null) {
			synchronized (OperatorController.class) {
				if(instance == null) {
					instance = new OperatorController();
				}
			}
		}
		
		return instance;
	}
	
	private OperatorController() {
		menuOperator = new MenuOperator();
		menuOperator.initialize();
		addHandlers(menuOperator);
	}

	// Menambahkan event handler onclick untuk tiap menu item
	private void addHandlers(MenuOperator mo) {
		// Make Report
		mo.menuItemMakeReport.setOnAction(e->{
			Main.changeScene(new MakeReport().initPage(role));
		});
		
		// View All Book
		mo.menuItemViewAllBook.setOnAction(e->{
			Main.changeScene(new ViewPCBooked().initPage(Main.user));
		});
		
		// Assign User to Another PC
		mo.menuItemAssignUser.setOnAction(e->{
			Main.changeScene(new ViewPCBooked().initPageAssign(Main.user));
		});
		
		// View All PC
		mo.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(new ViewAllPC().initPage(role));
		});
	}
	
	// Membuat window baru khusus assign user
	public void createAssignUser(PCBook pcBook, Integer pcId, ViewPCBookedVar viewPCBookedVar) {
		new ViewAssignUser().initPage(pcBook, pcId, viewPCBookedVar);
	}
}
