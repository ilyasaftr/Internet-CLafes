package view;

import controller.JobController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Job;
import view.ViewTechJob.ViewTechJobVar;

public class ViewTechJobDetail {

	// page khusus update job, detail job akan muncul dalam bentuk form yang un-editable
	// user tinggal ubah" field yang mau diupdate, lalu tekan tombol update
	
	public class ViewTechJobDetailVar{
		public Stage stage;
		Scene scene;

		VBox vb;
		Label titleLbl, Job_IDLbl, UserIDLbl, PC_IDLbl, JobStatusLbl;
		public TextField Job_IDTf, UserIDTf, PC_IDTf;
		public ComboBox<String> JobStatusCB;
		
		public Button btnUpdateJob;

		GridPane gp;
		
		public Alert alert;
		
		Job job;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewTechJobDetailVar components) {

		components.titleLbl = new Label("View PC Details");

		components.btnUpdateJob = new Button("Update Job");
		
		components.UserIDLbl = new Label("User ID:");
		components.PC_IDLbl = new Label("PC ID:");
		components.Job_IDLbl = new Label("Job ID");
		components.JobStatusLbl = new Label("Job Status:");
		
		components.Job_IDTf = new TextField();
		components.PC_IDTf = new TextField();
		components.UserIDTf = new TextField();
		components.JobStatusCB = new ComboBox<>();
		components.JobStatusCB.getItems().addAll("Complete", "UnComplete");
		
		components.gp = new GridPane();
		components.gp.add(components.Job_IDLbl, 0, 0);
		components.gp.add(components.UserIDLbl, 0, 1);
		components.gp.add(components.PC_IDLbl, 0, 2);
		components.gp.add(components.JobStatusLbl, 0, 3);
		components.gp.add(components.Job_IDTf, 1, 0);
		components.gp.add(components.UserIDTf, 1, 1);
		components.gp.add(components.PC_IDTf, 1, 2);
		components.gp.add(components.JobStatusCB, 1, 3);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.titleLbl, components.gp, components.btnUpdateJob);

		components.stage = new Stage();
		components.scene = new Scene(components.vb);
		components.stage.setScene(components.scene);
	}

	// getdata untuk mendapatkan data pc detail dari tabel
	private void getData(Integer jobID, ViewTechJobDetailVar components) {
		JobController jc = JobController.getInstance();
		components.job = jc.getJobByJobID(jobID);
		
		components.Job_IDTf.setText(components.job.getJob_ID().toString());
		components.PC_IDTf.setText(components.job.getPC_ID().toString());
		components.UserIDTf.setText(components.job.getUserID().toString());
		components.JobStatusCB.getSelectionModel().select(components.job.getJobStatus());
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewTechJobDetailVar components) {
		components.gp.setVgap(15);
		components.gp.setHgap(15);
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
		components.Job_IDTf.setEditable(false);
		components.Job_IDTf.setStyle("-fx-background-color: #d3d3d3;");
		
		components.PC_IDTf.setEditable(false);
		components.PC_IDTf.setStyle("-fx-background-color: #d3d3d3;");
		
		components.UserIDTf.setEditable(false);
		components.UserIDTf.setStyle("-fx-background-color: #d3d3d3;");
		
	}
	
	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 * Passing juga viewAllPCvar buat refresh tabel di view all pc window
	 */
	public void initPage(String role, Integer jobID, ViewTechJobVar components2) {
		// TODO Auto-generated method stub
		JobController jc = JobController.getInstance();
		ViewTechJobDetailVar components = new ViewTechJobDetailVar();
		
		initialize(components);
		setStyle(components);
		getData(jobID, components);
		initializeAlert(components);
		
		jc.addUpdateTechJobHandler(components, components2);
		
		components.stage.show();
	}
	
	// inisialisasi isi dari alert yang akan dimunculkan
	private void initializeAlert(ViewTechJobDetailVar components) {
		components.alert = new Alert(AlertType.ERROR);
		components.alert.setTitle("Error");
	}
}
