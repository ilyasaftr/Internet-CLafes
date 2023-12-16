package controller;

import java.time.LocalDate;
import java.util.Vector;

import dao.PCBookModel;
import model.PCBook;
import view.ViewAssignUser.ViewAssignUserVar;

public class PCBookController {
	public static volatile PCBookController instance = null;
	
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

	PCBookModel pcBookModel = PCBookModel.getInstance();
	
	public Vector<PCBook> GetPCBookedData(Integer PcID, LocalDate date) {
		return pcBookModel.GetPCBookedData(PcID, date);
	}

	public void addAssignPcHandler(ViewAssignUserVar components) {
		components.btnAssign.setOnAction(e -> {
			Integer newPCID;
			PCController pcCont = PCController.getInstance();
			
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
				components.stage.close();
			}
		});
	}

	private void assignUsertoNewPC(Integer BookID, int NewPCID) {
		pcBookModel.assignUsertoNewPC(BookID, NewPCID);
	}
	
}
