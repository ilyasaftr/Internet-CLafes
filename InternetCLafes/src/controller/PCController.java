package controller;

import java.util.Vector;

import dao.PCModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.PC;
import view.ViewAllPC.ViewAllPCVar;

public class PCController {
	// PC Controller menggunakan Singleton agar hanya satu instance yang terpakai di app.
	public static volatile PCController instance = null;
	
	private PCController() {
		
	}
	
	public static PCController getInstance() {
		if(instance == null) {
			synchronized (PCController.class) {
				if(instance == null) {
					 instance = new PCController();
				}
			}
		}
		
		return instance;
	}
	
	// Akses model untuk mengakses data PC dari database
	PCModel pcModel = PCModel.getInstance();

	public Vector<PC> getAllPCData() {
		return pcModel.getAllPCData();
	}

	public PC getPCDetail(Integer pcID) {
		return pcModel.getPCDetail(pcID);
	}

	// mengatur logic kalau ada record di table transaction header yang di select
	public void addViewPCDetailHandler(ViewAllPCVar components) {
		components.pcTable.setOnMouseClicked(e -> {
TableSelectionModel<PC> tableSelectionModel = components.pcTable.getSelectionModel();
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			// menampung selected item dari table
			PC pc = tableSelectionModel.getSelectedItem();
			
			// kalau pc ada yang di-select
			if(pc != null) {
				AdminController.getInstance().createPCDetailWindow(pc.getPC_ID());
			}
		});
	}

	public void addCreatePCHandler(ViewAllPCVar components) {
		components.btnCreatePC.setOnAction(e -> {
			if(components.pcIdTf.getText().isBlank()) {
				components.alert.setContentText("PC ID can not be empty");
				components.alert.showAndWait();
			}
			else if(getPCDetail(tryParseInt(components.pcIdTf.getText())) != null) {
				components.alert.setContentText("PC ID must be unique");
				components.alert.showAndWait();
			}
			else {
				pcModel.addNewPC(Integer.parseInt(components.pcIdTf.getText()));
			}
		});
	}

	private Integer tryParseInt(String text) {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return -1;
		}
	}
	
	
}
