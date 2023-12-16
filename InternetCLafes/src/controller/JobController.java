package controller;

import java.time.LocalDate;
import java.util.Vector;

import dao.JobModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableSelectionModel;
import model.Job;
import model.PCBook;
import model.User;
import view.ViewAllJob.ViewAllJobVar;
import view.ViewJobDetail.ViewJobDetailVar;

public class JobController {
	// Job Controller menggunakan Singleton agar hanya satu instance yang terpakai di app.
	public static volatile JobController instance = null;
	
	private JobController() {
		
	}
	
	public static JobController getInstance() {
		if(instance == null) {
			synchronized (JobController.class) {
				if(instance == null) {
					 instance = new JobController();
				}
			}
		}
		
		return instance;
	}

	// Akses model untuk mengakses data Job dari database
	JobModel jobModel = JobModel.getInstance();
	PCController pc = PCController.getInstance();
	PCBookController pcBookCont = PCBookController.getInstance();

	public void getTableData(ViewAllJobVar components) {
		components.jobTable.getItems().addAll(getAllJobData());
	}

	public Vector<Job> getAllJobData() {
		return jobModel.getAllJobData();
	}
	
	public void addAddJobHandler(ViewAllJobVar components) {
		components.btnAddJob.setOnAction(e -> {
			UserController uc = UserController.getInstance();
			
			User user;
			if(components.UserIDCB.getValue() == null) {
				user = null;
			}
			else {
				user = uc.getUserByUserID(components.UserIDCB.getValue());
				user = uc.getUserData(user.getUserName(), user.getUserPassword());
			}
			
			if(components.UserIDCB.getValue() == null) {
				components.alert.setContentText("User ID can not be empty");
				components.alert.showAndWait();
			}
			else if(user == null) {
				components.alert.setContentText("User ID must exist in database");
				components.alert.showAndWait();
			}
			else if(!user.getUserRole().equals("Computer Technician")) {
				components.alert.setContentText("User must be a Computer Technician");
				components.alert.showAndWait();
			}
			else if(components.PC_IDCB.getValue() == null) {
				components.alert.setContentText("PC ID can not be empty");
				components.alert.showAndWait();
			}
			else if(pc.getPCDetail(components.PC_IDCB.getValue()) == null) {
				components.alert.setContentText("PC ID must exist in database");
				components.alert.showAndWait();
			}
			else if(!getJobByPCID(components.PC_IDCB.getValue()).isEmpty()) {
				components.alert.setContentText("PC is currently worked on by technicians");
				components.alert.showAndWait();
			}
			else {
				
				Vector<PCBook> pcBookList =  pcBookCont.GetPCBookedData(components.PC_IDCB.getValue(), LocalDate.now());
				
				if(!pcBookList.isEmpty()) {
					for(PCBook pcBook : pcBookList) {
						AdminController.getInstance().createAssignUser(pcBook, components.PC_IDCB.getValue());
					}
				}
				
				addNewJob(components.UserIDCB.getValue(), components.PC_IDCB.getValue());
				refreshTable(components);
			}
		});
	}

	private Vector<Job> getJobByPCID(Integer pcID) {
		return jobModel.getJobByPCID(pcID);
	}

	public void addNewJob(Integer UserID, Integer PcID) {
		jobModel.addNewJob(UserID, PcID);
	}
	
	public void addUpdateJobHandler(ViewJobDetailVar viewJobDetailVar, ViewAllJobVar viewAllJobVar) {
		viewJobDetailVar.btnUpdateJob.setOnAction(e -> {
			if(!(viewJobDetailVar.JobStatusCB.getValue().equals("Complete") ||
					viewJobDetailVar.JobStatusCB.getValue().equals("UnComplete"))) {
				viewJobDetailVar.alert.setContentText("You must select a valid PC Condition");
				viewJobDetailVar.alert.showAndWait();
			}
			
			updateJobStatus(Integer.parseInt(viewJobDetailVar.Job_IDTf.getText()), viewJobDetailVar.JobStatusCB.getValue());
			
			pc.updatePCCondition(Integer.parseInt(viewJobDetailVar.PC_IDTf.getText()), (viewJobDetailVar.JobStatusCB.getValue().equals("Complete"))? "Usable": "Maintenance");
			
			refreshTable(viewAllJobVar);
			
			// kalau sudah update, tutup window
			viewJobDetailVar.stage.close();
		});
	}
	
	public void updateJobStatus(Integer JobID, String JobStatus) {
		jobModel.updateJobStatus(JobID, JobStatus);
	}
	
	public Job getTechnicianJob(Integer UserID) {
		return jobModel.getTechnicianJob(UserID);
	}
	
	public Job getJobByJobID(Integer jobID) {
		return jobModel.getJobByJobID(jobID);
	}
	
	public void addViewJobDetailHandler(ViewAllJobVar components) {
		components.jobTable.setOnMouseClicked(e -> {
			TableSelectionModel<Job> tableSelectionModel = 			components.jobTable.getSelectionModel();
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			// menampung selected item dari table
			Job job = tableSelectionModel.getSelectedItem();
			
			// kalau pc ada yang di-select
			if(job != null) {
				AdminController.getInstance().createJobDetailWindow(job.getJob_ID(), components);
			}
		});
	}

	private void refreshTable(ViewAllJobVar components) {
		components.jobTable.getItems().clear();
		getTableData(components);
	}
}
