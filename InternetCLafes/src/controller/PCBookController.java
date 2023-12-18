package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dao.PCBookModel;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.PCBook;
import view.MakePCBook.PCBookVar;
import view.ViewPCBooked.ViewPCBookedVar;
import view.ViewAssignUser.ViewAssignUserVar;

public class PCBookController {
	// PCBookController menggunakan Singleton agar hanya satu instance yang terpakai
	// di app.
	public static volatile PCBookController instance = null;

	private PCBookController() {

	}

	public static PCBookController getInstance() {
		if (instance == null) {
			synchronized (PCBookController.class) {
				if (instance == null) {
					instance = new PCBookController();
				}
			}
		}

		return instance;
	}

	// Akses ke database melalui pcBookModel
	PCBookModel pcBookModel = PCBookModel.getInstance();

	// mendapatkan semua data user
	public Vector<PCBook> getAllPcBookedData() {
		return pcBookModel.getAllPcBookedData();
	}

	/*
	 * getData digunakan untuk mendapatkan data staff semua yang akan dimasukkan ke
	 * tabel
	 */
	public void getData(ViewPCBookedVar components) {
		components.pcBookList = getAllPcBookedData();
		components.pcBookTable.getItems().clear();

		for (PCBook pcbook : components.pcBookList) {
			components.pcBookTable.getItems().add(pcbook);
		}
	}

	// Mendapatkan list PCBook yang tanggalnya masih tanggal-tanggal ke depan dari
	// sekarang berdasarkan PC ID
	public Vector<PCBook> GetPCBookedData(Integer PcID, LocalDate date) {
		return pcBookModel.GetPCBookedData(PcID, date);
	}

	// Event handler untuk assign pc saat add PC
	public void addAssignPcHandler(ViewAssignUserVar components) {
		components.btnAssign.setOnAction(e -> {
			Integer newPCID;
			PCController pcCont = PCController.getInstance();

			// Validasi apakah new PC ID sudah terisi, numerik, bilangan positif, ada di
			// database, dan belum ada PC Book
			try {
				newPCID = Integer.parseInt(components.newPCIDTf.getText());
			} catch (Exception e1) {
				newPCID = null;
			}

			if (components.newPCIDTf.getText().isBlank()) {
				components.alert.setContentText("New PC ID can not be empty");
				components.alert.showAndWait();
			} else if (newPCID == null) {
				components.alert.setContentText("New PC ID must be numeric");
				components.alert.showAndWait();
			} else if (newPCID <= 0) {
				components.alert.setContentText("New PC ID must be a positive number");
				components.alert.showAndWait();
			} else if (pcCont.getPCDetail(newPCID) == null) {
				components.alert.setContentText("New PC ID does not exist in database");
				components.alert.showAndWait();
			} else if (!GetPCBookedData(newPCID, LocalDate.now()).isEmpty()) {
				components.alert.setContentText("New PC ID is already booked");
				components.alert.showAndWait();
			} else {
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

	// Pindahkan user ke PC baru
	public void deleteBookData(Integer BookID) {
		pcBookModel.deleteBookData(BookID);
	}
	
	private void refreshTable(ViewPCBookedVar components) {
		components.pcBookTable.getItems().clear();
		getData(components);
	}
	
	// handler event view PC Booked page
	public void addViewPCBookedHandler(ViewPCBookedVar components, int StaffID) {
		List<PCBook> selectedPCBook = new ArrayList<>();

		// mengatur logic kalau ada record di table pc book yang di select
		components.pcBookTable.setOnMouseClicked(e -> {
			TableSelectionModel<PCBook> tableSelectionModel = components.pcBookTable.getSelectionModel();

			tableSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
			
			// menampung selected item dari table
			ObservableList<PCBook> pcbookList = tableSelectionModel.getSelectedItems();
			pcbookList.addListener((javafx.collections.ListChangeListener.Change<? extends PCBook> change) -> {
				selectedPCBook.clear();
				selectedPCBook.addAll(pcbookList);

			});

		});
		

		// atur logic kalau button finish book ditekan
		components.btnFinishBook.setOnAction(e -> {
			pcBookModel.finishBook(selectedPCBook);
			
			for(PCBook pcBook: selectedPCBook) {
				if(!pcBook.getBookedDate().isBefore(LocalDate.now())) {
					components.invalidDataAlert.setContentText("PC Booked Data Has Not Passed Yet");
					components.invalidDataAlert.showAndWait();
					return;
				}
			}
			
			for(PCBook pcBook: selectedPCBook) {
				pcBookModel.deleteBookData(pcBook.getBook_ID());
			}
			
			TransactionController transControl = TransactionController.getInstance();
			transControl.addTransaction(selectedPCBook, StaffID);
			
			components.successAlert.setContentText("Book PC successfully finished");
			components.successAlert.showAndWait();
			refreshTable(components);
		});
		
		// atur logic kalau button cancel book ditekan
		components.btnCancelBook.setOnAction(e -> {
			if(selectedPCBook.isEmpty()) {
				components.noRowAlert.showAndWait();
			}
			// asumsi cancel book hanya bisa satu per satu
			else if(selectedPCBook.size() != 1) {
				components.invalidDataAlert.setContentText("You must select only one PC Book data");
				components.invalidDataAlert.showAndWait();
			}
			else if(selectedPCBook.get(0).getBookedDate().isAfter(LocalDate.now())) {
				components.invalidDataAlert.setContentText("PC Booked Date already passed today's date");
				components.invalidDataAlert.showAndWait();
			}
			else {
				pcBookModel.deleteBookData(selectedPCBook.get(0).getBook_ID());
				components.successAlert.setContentText("Book PC successfully cancelled");
				components.successAlert.showAndWait();
				refreshTable(components);
			}
		});
	}

	// Event handler ketika book pc
	public void addPCBookHandler(PCBookVar components, int UserID) {
		components.btnSubmit.setOnAction(e -> {
			PCController pcCont = PCController.getInstance();
			
			if (components.PC_IDCB.getValue() == null) {
				components.errorAlert.setContentText("PC ID can not be empty");
				components.errorAlert.showAndWait();
			}
			// validasi apakah PC condition usable
			else if(pcCont.getPCDetail(components.PC_IDCB.getValue()).getPC_Condition().equals("Maintenance") ||
					pcCont.getPCDetail(components.PC_IDCB.getValue()).getPC_Condition().equals("Broken")) {
				components.errorAlert.setContentText("PC is currently unavailable\nPick another PC");
				components.errorAlert.showAndWait();
			}
			else if (components.BookDateDP.getValue() == null) {
				components.errorAlert.setContentText("Book Date can not be empty");
				components.errorAlert.showAndWait();
			} else {
				Vector<PCBook> pcBookResult = pcBookModel.GetPCBookedData(components.PC_IDCB.getValue(), components.BookDateDP.getValue());
				if (pcBookResult.size() >= 1) {
					components.errorAlert.setContentText("You can't book on that date!");
					components.errorAlert.showAndWait();
					return;
				}
				addNewPCBook(components.PC_IDCB.getValue(), UserID, components.BookDateDP.getValue());
				components.successAlert.setContentText("Book PC successfully booked");
				components.successAlert.showAndWait();
			}
		});
	}
}