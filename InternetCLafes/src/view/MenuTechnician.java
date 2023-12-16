package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuTechnician {
	// menu bar khusus user dengan role Computer Technician
	
	public MenuBar menuBar;
	Menu menuPC, menuJob;
	public MenuItem menuItemViewAllPC, menuItemViewAllJob, menuItemCompleteJob;

	public void initialize() {
		menuBar = new MenuBar();

		menuPC = new Menu("PC");
		menuJob = new Menu("Job");
		
		menuBar.getMenus().addAll(menuPC, menuJob);

		menuItemViewAllPC = new MenuItem("View All PCs");
		menuItemViewAllJob = new MenuItem("View All Jobs");
		menuItemCompleteJob = new MenuItem("Complete Job");
		
		menuPC.getItems().addAll(menuItemViewAllPC);
		menuJob.getItems().addAll(menuItemViewAllJob, menuItemCompleteJob);
		
	}
}
