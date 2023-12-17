package controller;

import main.Main;
import view.MakeReport;
import view.MenuOperator;
import view.ViewAllPC;

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
			Main.changeScene(null);
		});
		
		// View All PC
		mo.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(new ViewAllPC().initPage(role));
		});
	}
}
