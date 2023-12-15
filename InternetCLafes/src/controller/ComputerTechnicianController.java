package controller;

import main.Main;
import view.MenuTechnician;
import view.ViewAllPC;

public class ComputerTechnicianController {
	// Class ini dianggap sebagai Computer Technician Menu Controller, dia yang mengatur event handler dan validasi dari bagian Menu dari role Computer Technician
	
	public MenuTechnician menuTechnician;
	private final String role = "Computer Technician";
	
	public static volatile ComputerTechnicianController instance = null;
	
	public static ComputerTechnicianController getInstance() {
		if(instance == null) {
			synchronized (ComputerTechnicianController.class) {
				if(instance == null) {
					instance = new ComputerTechnicianController();
				}
			}
		}
		
		return instance;
	}
	
	private ComputerTechnicianController() {
		menuTechnician = new MenuTechnician();
		menuTechnician.initialize();
		addHandlers(menuTechnician);
	}

	private void addHandlers(MenuTechnician mt) {
		mt.menuItemCompleteJob.setOnAction(e->{
			Main.changeScene(null);
		});
		mt.menuItemViewAllPC.setOnAction(e->{
			Main.changeScene(new ViewAllPC().initPage(role));
		});
		mt.menuItemViewAllTechJob.setOnAction(e->{
			Main.changeScene(null);
		});
	}
}
