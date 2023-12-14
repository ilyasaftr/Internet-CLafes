package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuCustomer {
	
	// menu bar khusus user dengan role Customer
	
	public MenuBar menuBar;
	Menu menuPC, menuTransaction, menuBook, menuReport;
	public MenuItem menuItemViewAllPC, menuItemBookPC, menuItemViewCustomerTransaction, menuItemMakeReport;

	public void initialize() {
		menuBar = new MenuBar();

		menuPC = new Menu("PC");
		menuTransaction = new Menu("Transaction");
		menuBook = new Menu("Book");
		menuReport = new Menu("Report");
		
		menuBar.getMenus().addAll(menuPC, menuBook, menuReport, menuTransaction);

		menuItemViewAllPC = new MenuItem("View All PCs");
		menuItemBookPC = new MenuItem("Book PC");
		menuItemViewCustomerTransaction = new MenuItem("View All Transactions");
		menuItemMakeReport = new MenuItem("Make Report");
		
		menuPC.getItems().addAll(menuItemViewAllPC);
		menuBook.getItems().addAll(menuItemBookPC);
		menuReport.getItems().addAll(menuItemMakeReport);
		menuTransaction.getItems().addAll(menuItemViewCustomerTransaction);
	}
}


