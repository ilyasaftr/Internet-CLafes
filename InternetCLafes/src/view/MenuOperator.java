package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuOperator {

	// menu bar khusus user dengan role Operator
	
	public MenuBar menuBar;
	Menu menuPC, menuBook, menuReport;
	public MenuItem menuItemViewAllPC, menuItemViewAllBook, menuItemMakeReport;

	public void initialize() {
		menuBar = new MenuBar();

		menuPC = new Menu("PC");
		menuBook = new Menu("Book");
		menuReport = new Menu("Report");
		
		menuBar.getMenus().addAll(menuPC, menuBook, menuReport);

		menuItemViewAllPC = new MenuItem("View All PCs");
		menuItemViewAllBook = new MenuItem("View PC Booked Data");
		menuItemMakeReport = new MenuItem("Make Report");
		
		menuPC.getItems().addAll(menuItemViewAllPC);
		menuBook.getItems().addAll(menuItemViewAllBook);
		menuReport.getItems().addAll(menuItemMakeReport);
	}
}
