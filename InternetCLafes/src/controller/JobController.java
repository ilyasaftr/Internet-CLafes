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
	
	// Akses controller PC dan PCBook
	PCController pc = PCController.getInstance();
	PCBookController pcBookCont = PCBookController.getInstance();

	// Mendapatkan data untuk tabel view ViewAllJob
	public void getTableData(ViewAllJobVar components) {
		components.jobTable.getItems().addAll(getAllJobData());
	}

	// Mendapatkan list isinya semua data job dari database lewat model
	public Vector<Job> getAllJobData() {
		return jobModel.getAllJobData();
	}
	
	// Menambahkan event handler Add Job
	public void addAddJobHandler(ViewAllJobVar components) {
		components.btnAddJob.setOnAction(e -> {
			UserController uc = UserController.getInstance();
			
			User user;
			
			// Validasi apa terisi ComboBox UserID
			if(components.UserIDCB.getValue() == null) {
				user = null;
			}
			else {
				user = uc.getUserByUserID(components.UserIDCB.getValue());
				user = uc.getUserData(user.getUserName(), user.getUserPassword());
			}
			
			// Validasi apakah user ID numerik, bilangan positif, dan memiliki role Computer Technician
			// Validasi apakah PC ID terisi, ada di database, dan tidak ada job.
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
				
				// kalau ada pcBook di waktu sekarang dan ke depannya, pindahkan user pengguna pc yang bersangkutan ke pc baru
				
				Vector<PCBook> pcBookList =  pcBookCont.GetFuturePCBookedData(components.PC_IDCB.getValue(), LocalDate.now());
				
				if(!pcBookList.isEmpty()) {
					for(PCBook pcBook : pcBookList) {
						AdminController.getInstance().createAssignUser(pcBook, components.PC_IDCB.getValue());
					}
				}
				
				// lalu tambahkan job baru
				addNewJob(components.UserIDCB.getValue(), components.PC_IDCB.getValue());
				
				// lalu update pc condition jadi maintenance
				pc.updatePCCondition(components.PC_IDCB.getValue(), "Maintenance");
				
//				perbarui data tabel
				refreshTable(components);
			}
		});
	}

	// mendapatkan job dari pc id
	private Vector<Job> getJobByPCID(Integer pcID) {
		return jobModel.getJobByPCID(pcID);
	}

	// menambahkan job baru ke database
	public void addNewJob(Integer UserID, Integer PcID) {
		jobModel.addNewJob(UserID, PcID);
	}
	
	// mengurus event handler untuk update job
	public void addUpdateJobHandler(ViewJobDetailVar viewJobDetailVar, ViewAllJobVar viewAllJobVar) {
		viewJobDetailVar.btnUpdateJob.setOnAction(e -> {
			// validasi apakah job status combo box isinya Complete atau UnComplete
			if(!(viewJobDetailVar.JobStatusCB.getValue().equals("Complete") ||
					viewJobDetailVar.JobStatusCB.getValue().equals("UnComplete"))) {
				viewJobDetailVar.alert.setContentText("You must select a valid PC Condition");
				viewJobDetailVar.alert.showAndWait();
			}
			
			// kalau sudah valid, update job status
			updateJobStatus(Integer.parseInt(viewJobDetailVar.Job_IDTf.getText()), viewJobDetailVar.JobStatusCB.getValue());
			
			// lalu setelah itu, update juga PC Condition
			// kalau Job complete, PC jadi Usable
			// kalau UnComplete, PC jadi Maintenance
			pc.updatePCCondition(Integer.parseInt(viewJobDetailVar.PC_IDTf.getText()), (viewJobDetailVar.JobStatusCB.getValue().equals("Complete"))? "Usable": "Maintenance");
			
			// perbarui data tabel
			refreshTable(viewAllJobVar);
			
			// kalau sudah update, tutup window
			viewJobDetailVar.stage.close();
		});
	}
	
	// untuk memperbarui data jobstatus dari sebuah job
	public void updateJobStatus(Integer JobID, String JobStatus) {
		jobModel.updateJobStatus(JobID, JobStatus);
	}
	
	// mendapatkan job dari userID
	public Job getTechnicianJob(Integer UserID) {
		return jobModel.getTechnicianJob(UserID);
	}
	
	// mendapatkan job dari jobId
	public Job getJobByJobID(Integer jobID) {
		return jobModel.getJobByJobID(jobID);
	}
	
	// event handler dari View Job Detail
	// kalau tekan tombol, dia akan buka window baru isinya job detail
	public void addViewJobDetailHandler(ViewAllJobVar components) {
		components.jobTable.setOnMouseClicked(e -> {
			TableSelectionModel<Job> tableSelectionModel = 			components.jobTable.getSelectionModel();
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			
			// menampung selected item dari table
			Job job = tableSelectionModel.getSelectedItem();
			
			// kalau job ada yang di-select
			if(job != null) {
				AdminController.getInstance().createJobDetailWindow(job.getJob_ID(), components);
			}
		});
	}

	// perbarui data tabel
	private void refreshTable(ViewAllJobVar components) {
		components.jobTable.getItems().clear();
		getTableData(components);
	}
}
