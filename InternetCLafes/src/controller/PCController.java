package controller;

import java.time.LocalDate;
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
	
	public Vector<Integer> getAllPCID(){
		Vector<Integer> PCIDList = new Vector<>();
		
		for(PC pc : getAllPCData()) {
			PCIDList.add(pc.getPC_ID());
		}
		
		return PCIDList;
	}

	public PC getPCDetail(Integer pcID) {
		return pcModel.getPCDetail(pcID);
	}

	// mengatur logic kalau menekan record pc yang ada di tabel sehingga muncul window baru isinya pc detail serta pilihan update delete
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

	// menambahkan event handler on click kalau mau tambahkan pc
	public void addCreatePCHandler(ViewAllPCVar components) {
		components.btnCreatePC.setOnAction(e -> {
			// kalau pc id kosong
			if(components.pcIdTf.getText().isBlank()) {
				components.alert.setContentText("PC ID can not be empty");
				components.alert.showAndWait();
			}
			// kalau pc id bukan digit
			else if(!tryParseInt(components.pcIdTf.getText())) {
				components.alert.setContentText("PC ID must be numeric");
				components.alert.showAndWait();
			}
			// kalau pc id bukan bilangan positif
			else if(Integer.parseInt(components.pcIdTf.getText()) <= 0) {
				components.alert.setContentText("PC ID must be a positive number");
				components.alert.showAndWait();
			}
			// kalau pc id sudah ada
			else if(getPCDetail(Integer.parseInt(components.pcIdTf.getText())) != null) {
				components.alert.setContentText("PC ID must be unique");
				components.alert.showAndWait();
			}
			else {
				addNewPC(Integer.parseInt(components.pcIdTf.getText()));
				// kosongkan tabel dan refill dengan data baru
				refreshTable(components);
				// kosongkan form
				refreshForm(components);
			}
		});
	}
	
	public void addNewPC(int pcId) {
		pcModel.addNewPC(pcId);
	}
	
	private void refreshForm(ViewAllPCVar components) {
		components.pcIdTf.setText("");
	}

	// memperbarui data tabel setelah Create - Update - Delete operation
	private void refreshTable(ViewAllPCVar components) {
		components.pcTable.getItems().clear();
		components.pcList.clear();
		getData(components);
	}
	
	// Memasukkan data ke tabel dari database
	public void getData(ViewAllPCVar components) {
		PCController pcControl = PCController.getInstance();
		components.pcList = pcControl.getAllPCData();

		for(PC pc : components.pcList) {
			components.pcTable.getItems().add(pc);
		}
	}

	// coba parse int, return true kalau int, false kalau selain int
	private boolean tryParseInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// mengurus update pc detail tepatnya condition
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
				updatePCCondition(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()), viewPCDetailVar.pcCondTf.getValue());
				refreshTable(viewAllPCVar);
				// kalau sudah update, tutup window pc detail
				viewPCDetailVar.stage.close();
			}
		});
	}

	public void updatePCCondition(int PcID, String Condition) {
		pcModel.updatePCCondition(PcID, Condition);
	}

	// mengurus kalau user mau hapus PC
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
				PCBookController pcBookControl = PCBookController.getInstance();
				
				if(!pcBookControl.GetPCBookedData(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()), LocalDate.now()).isEmpty()) {
					viewPCDetailVar.alert.setContentText("PC still has book list in the future");
					viewPCDetailVar.alert.showAndWait();
				}
				else {
					deletePC(Integer.parseInt(viewPCDetailVar.pcIdTf.getText()));
					refreshTable(viewAllPCVar);
					// kalau sudah delete, tutup window pc detail
					viewPCDetailVar.stage.close();
				}
			}
		});
	}

	private void deletePC(int PcID) {
		pcModel.deletePC(PcID);
	}
	
	
}
