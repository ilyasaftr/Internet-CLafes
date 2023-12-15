package controller;

import java.util.Vector;

import dao.PCModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.PC;
import view.ViewAllPC.ViewAllPCVar;
import view.ViewPCDetail.ViewPCDetailVar;

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
				AdminController.getInstance().createPCDetailWindow(pc.getPC_ID(), components);
			}
		});
	}

	public void addCreatePCHandler(ViewAllPCVar components) {
		components.btnCreatePC.setOnAction(e -> {
			if(components.pcIdTf.getText().isBlank()) {
				components.alert.setContentText("PC ID can not be empty");
				components.alert.showAndWait();
			}
			else if(!tryParseInt(components.pcIdTf.getText())) {
				components.alert.setContentText("PC ID must be numeric");
				components.alert.showAndWait();
			}
			else if(Integer.parseInt(components.pcIdTf.getText()) <= 0) {
				components.alert.setContentText("PC ID must be a positive number");
				components.alert.showAndWait();
			}
			else if(getPCDetail(Integer.parseInt(components.pcIdTf.getText())) != null) {
				components.alert.setContentText("PC ID must be unique");
				components.alert.showAndWait();
			}
			else {
				pcModel.addNewPC(Integer.parseInt(components.pcIdTf.getText()));
				refreshTable(components);
			}
		});
	}

	/*
	 * getData digunakan untuk mendapatkan data pc semua yang akan dimasukkan ke tabel
	 */
	
	private void refreshTable(ViewAllPCVar components) {
		components.pcTable.getItems().clear();
		components.pcList.clear();
		getData(components);
	}
	
	public void getData(ViewAllPCVar components) {
		PCController pcControl = PCController.getInstance();
		components.pcList = pcControl.getAllPCData();

		for(PC pc : components.pcList) {
			components.pcTable.getItems().add(pc);
		}
	}

	private boolean tryParseInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void addUpdatePCHandler(ViewPCDetailVar viewPCDetailVar, ViewAllPCVar viewAllPCVar) {
		viewPCDetailVar.btnUpdate.setOnAction(e -> {
			if(viewPCDetailVar.pcIdTf.getText().isBlank()) {
				viewPCDetailVar.alert.setContentText("You must select a PC");
				viewPCDetailVar.alert.showAndWait();
			}
			else if(!tryParseInt(viewPCDetailVar.pcIdTf.getText())) {
				viewPCDetailVar.alert.setContentText("PC ID must be numeric");
				viewPCDetailVar.alert.showAndWait();
			}
			else if(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()) <= 0) {
				viewPCDetailVar.alert.setContentText("PC ID must be a positive number");
				viewPCDetailVar.alert.showAndWait();
			}
			else if(!(viewPCDetailVar.pcCondTf.getValue().equals("Usable") ||
					viewPCDetailVar.pcCondTf.getValue().equals("Maintenance") ||
					viewPCDetailVar.pcCondTf.getValue().equals("Broken"))) {
				viewPCDetailVar.alert.setContentText("You must select a valid PC Condition");
				viewPCDetailVar.alert.showAndWait();
			}
			else {
				pcModel.updatePCCondition(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()), viewPCDetailVar.pcCondTf.getValue());
				refreshTable(viewAllPCVar);
				viewPCDetailVar.stage.close();
			}
		});
	}

	public void addDeletePCHandler(ViewPCDetailVar viewPCDetailVar, ViewAllPCVar viewAllPCVar) {
		viewPCDetailVar.btnDelete.setOnAction(e -> {
			if(viewPCDetailVar.pcIdTf.getText().isBlank()) {
				viewPCDetailVar.alert.setContentText("You must select a PC");
				viewPCDetailVar.alert.showAndWait();
			}
			else if(!tryParseInt(viewPCDetailVar.pcIdTf.getText())) {
				viewPCDetailVar.alert.setContentText("PC ID must be numeric");
				viewPCDetailVar.alert.showAndWait();
			}
			else if(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()) <= 0) {
				viewPCDetailVar.alert.setContentText("PC ID must be a positive number");
				viewPCDetailVar.alert.showAndWait();
			}
			else if(!(viewPCDetailVar.pcCondTf.getValue().equals("Usable") ||
					viewPCDetailVar.pcCondTf.getValue().equals("Maintenance") ||
					viewPCDetailVar.pcCondTf.getValue().equals("Broken"))) {
				viewPCDetailVar.alert.setContentText("You must select a valid PC Condition");
				viewPCDetailVar.alert.showAndWait();
			}
else {
				pcModel.deletePC(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()));
				refreshTable(viewAllPCVar);
				viewPCDetailVar.stage.close();
			}
		});
	}
	
	
}
