package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuAdmin {

	// menu bar khusus user dengan role Admin
	
	public MenuBar menuBar;
	Menu menuPC, menuTransaction, menuJob, menuStaff, menuReport;
	public MenuItem menuItemViewAllPC, menuItemViewAllTransaction, menuItemViewAllReport, menuItemViewAllStaff, menuItemViewAllJob;

	public void initialize() {
		menuBar = new MenuBar();

		menuPC = new Menu("PC");
		menuTransaction = new Menu("Transaction");
		menuJob = new Menu("Job");
		menuStaff = new Menu("Staff"); 
		menuReport = new Menu("Report");
		
		menuBar.getMenus().addAll(menuPC, menuJob, menuStaff, menuReport, menuTransaction);

		menuItemViewAllPC = new MenuItem("View All PCs");
		menuItemViewAllTransaction = new MenuItem("View All Transactions");
		menuItemViewAllReport = new MenuItem("View All Reports");
		menuItemViewAllStaff = new MenuItem("View All Staff");
		menuItemViewAllJob = new MenuItem("View All Jobs");
		
		menuPC.getItems().addAll(menuItemViewAllPC);
		menuJob.getItems().addAll(menuItemViewAllJob);
		menuStaff.getItems().addAll(menuItemViewAllStaff);
		menuReport.getItems().addAll(menuItemViewAllReport);
		menuTransaction.getItems().addAll(menuItemViewAllTransaction);
	}
}
