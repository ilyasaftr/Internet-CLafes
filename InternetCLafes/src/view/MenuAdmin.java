package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuAdmin {

	MenuBar menuBar;
	Menu menuPC, menuTransaction, menuJob, menuStaff, menuReport;
	public MenuItem menuItemViewAllPC, menuItemAddPC, menuItemViewAllTransaction, menuItemViewAllReport, menuItemViewAllStaff, menuItemViewAllTechJob, menuItemAddTechJob;

	public void initialize() {
		menuBar = new MenuBar();

		menuPC = new Menu("PC");
		menuTransaction = new Menu("Transaction");
		menuJob = new Menu("Technician Job");
		menuStaff = new Menu("Staff"); 
		menuReport = new Menu("Report");
		
		menuBar.getMenus().addAll(menuPC, menuJob, menuStaff, menuReport, menuTransaction);

		menuItemViewAllPC = new MenuItem("View All PCs");
		menuItemAddPC = new MenuItem("Add PC");
		menuItemViewAllTransaction = new MenuItem("View All Transactions");
		menuItemViewAllReport = new MenuItem("View All Reports");
		menuItemViewAllStaff = new MenuItem("View All Staff");
		menuItemViewAllTechJob = new MenuItem("View All Technician Jobs");
		menuItemAddTechJob = new MenuItem("Add Technician Job");
		
		menuPC.getItems().addAll(menuItemAddPC, menuItemViewAllPC);
		menuJob.getItems().addAll(menuItemAddTechJob, menuItemViewAllTechJob);
		menuStaff.getItems().addAll(menuItemViewAllStaff);
		menuReport.getItems().addAll(menuItemViewAllReport);
		menuTransaction.getItems().addAll(menuItemViewAllTransaction);
	}
}
