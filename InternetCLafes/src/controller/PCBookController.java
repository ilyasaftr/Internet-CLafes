package controller;

import java.time.LocalDate;
import java.util.Vector;

import dao.PCBookModel;
import model.PCBook;
import view.PCBook.PCBookVar;
import view.ViewAssignUser.ViewAssignUserVar;

public class PCBookController {
	// PCBookController menggunakan Singleton agar hanya satu instance yang terpakai di app.
	public static volatile PCBookController instance = null;
	PCController pcCont = PCController.getInstance();
	private PCBookController() {
		
	}
	
	public static PCBookController getInstance() {
		if(instance == null) {
			synchronized (PCBookController.class) {
				if(instance == null) {
					 instance = new PCBookController();
				}
			}
		}
		
		return instance;
	}

	// Akses ke database melalui pcBookModel
	PCBookModel pcBookModel = PCBookModel.getInstance();
	
	// Mendapatkan list PCBook yang tanggalnya masih tanggal-tanggal ke depan dari sekarang berdasarkan PC ID
	public Vector<PCBook> GetPCBookedData(Integer PcID, LocalDate date) {
		return pcBookModel.GetPCBookedData(PcID, date);
	}

	// Event handler untuk assign pc saat add PC
	public void addAssignPcHandler(ViewAssignUserVar components) {
		components.btnAssign.setOnAction(e -> {
			Integer newPCID;
			
			// Validasi apakah new PC ID sudah terisi, numerik, bilangan positif, ada di database, dan belum ada PC Book
			try {
				newPCID = Integer.parseInt(components.newPCIDTf.getText());
			} catch (Exception e1) {
				newPCID = null;
			}
			
			if(components.newPCIDTf.getText().isBlank()) {
				components.alert.setContentText("New PC ID can not be empty");
				components.alert.showAndWait();
			}
			else if(newPCID == null) {
				components.alert.setContentText("New PC ID must be numeric");
				components.alert.showAndWait();
			}
			else if(newPCID <= 0) {
				components.alert.setContentText("New PC ID must be a positive number");
				components.alert.showAndWait();
			}
			else if(pcCont.getPCDetail(newPCID) == null) {
				components.alert.setContentText("New PC ID does not exist in database");
				components.alert.showAndWait();
			}
			else if(!GetPCBookedData(newPCID, LocalDate.now()).isEmpty()) {
				components.alert.setContentText("New PC ID is already booked");
				components.alert.showAndWait();
			}
			else {
				assignUsertoNewPC(components.pcBook.getBook_ID(), Integer.parseInt(components.newPCIDTf.getText()));
				// tutup halaman view assign user setelah assign user
				components.stage.close();
			}
		});
	}

	// Pindahkan user ke PC baru
	private void assignUsertoNewPC(Integer BookID, int NewPCID) {
		pcBookModel.assignUsertoNewPC(BookID, NewPCID);
	}
	
	// Tambahkan Book PC baru
	private void addNewPCBook(int PcID, int UserID, LocalDate BookDP) {
		pcBookModel.addNewPCBook(PcID, UserID, BookDP);
	}
	
	// Event handler ketika book pc
	public void addPCBookHandler(PCBookVar components, int UserID) {
		components.btnSubmit.setOnAction(e -> {
			if(components.PC_IDCB.getValue() == null) {
				components.errorAlert.setContentText("PC ID can not be empty");
				components.errorAlert.showAndWait();
			}
			// validasi apakah PC condition usable
			else if(pcCont.getPCDetail(components.PC_IDCB.getValue()).getPC_Condition().equals("Maintenance") ||
					pcCont.getPCDetail(components.PC_IDCB.getValue()).getPC_Condition().equals("Broken")) {
				components.errorAlert.setContentText("PC is currently unavailable\nPick another PC");
				components.errorAlert.showAndWait();
			}
			else if(components.BookDateDP.getValue() == null) {
				components.errorAlert.setContentText("Book Date can not be empty");
				components.errorAlert.showAndWait();
			}
			else {
				addNewPCBook(components.PC_IDCB.getValue(), UserID, components.BookDateDP.getValue());
				components.successAlert.setContentText("Book PC successfully booked");
				components.successAlert.showAndWait();
			}
		});
	}
}
