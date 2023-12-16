package view;

import controller.JobController;
import controller.MenuController;
import controller.PCController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Job;

public class ViewAllJob {

	// isi classnya tabel isinya semua staff job (view all jobs)
	// di halaman ini juga bisa add job
	// kalau select row, datanya akan ditampil ke window baru untuk update
	
	/*
	 * ViewAllJobVar berisi semua komponen UI yang digunakan di page ViewAllJob
	 */
	public class ViewAllJobVar{
		Scene scene;

		BorderPane bp;

		VBox vb;
		Label titleLbl, UserIDLbl, PC_IDLbl;
		public ComboBox<Integer> UserIDCB, PC_IDCB;
		
		public Button btnAddJob;

		ScrollPane sp;
		GridPane gp;
		
		public TableView<Job> jobTable;
		TableColumn<Job, Integer> Job_IDCol;
		TableColumn<Job, Integer> UserIDCol;
		TableColumn<Job, Integer> PC_IDCol;
		TableColumn<Job, String> JobStatusCol;
		
		public Alert alert;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewAllJobVar components) {

		UserController uc = UserController.getInstance();
		PCController pc = PCController.getInstance();
		
		components.titleLbl = new Label("View All Staff Jobs");

		initializeTable(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.jobTable);
		components.sp.setFitToWidth(true);
		
		components.btnAddJob = new Button("Add Job");
		
		components.UserIDLbl = new Label("User ID:");
		components.PC_IDLbl = new Label("PC ID:");
		
		components.UserIDCB = new ComboBox<>();
		components.UserIDCB.getItems().addAll(uc.getAllTechnicianID());
		
		components.PC_IDCB = new ComboBox<>();
		components.PC_IDCB.getItems().addAll(pc.getAllPCID());
		
		components.gp = new GridPane();
		components.gp.add(components.UserIDLbl, 0, 0);
		components.gp.add(components.UserIDCB, 1, 0);
		components.gp.add(components.PC_IDLbl, 0, 1);
		components.gp.add(components.PC_IDCB, 1, 1);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.sp, components.gp, components.btnAddJob);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);
		
		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewAllJobVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.Job_IDCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(4).multiply(1));
		components.JobStatusCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(4).multiply(1));
		components.PC_IDCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(4).multiply(1));
		components.UserIDCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(4).multiply(1));
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
		components.gp.setVgap(10);
		components.gp.setHgap(15);
		
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel, kolom-kolomnya juga
	 */
	private void initializeTable(ViewAllJobVar components) {
		components.jobTable = new TableView<>();

		components.Job_IDCol = new TableColumn<>("Job ID");
		components.JobStatusCol = new TableColumn<>("Job Status");
		components.PC_IDCol = new TableColumn<>("PC ID");
		components.UserIDCol = new TableColumn<>("User ID");

		components.jobTable.getColumns().add(components.Job_IDCol);
		components.jobTable.getColumns().add(components.JobStatusCol);
		components.jobTable.getColumns().add(components.PC_IDCol);
		components.jobTable.getColumns().add(components.UserIDCol);
		
		components.Job_IDCol.setCellValueFactory(new PropertyValueFactory<>("Job_ID"));
		components.JobStatusCol.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
		components.PC_IDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		components.UserIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
		
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(String role) {
		JobController jc = JobController.getInstance();
		MenuController mc = MenuController.getInstance();
		
		ViewAllJobVar components = new ViewAllJobVar();
		initialize(components);
		jc.getTableData(components);
		
		setStyle(components);
		initializeAlert(components);
		mc.selectMenuType(components.bp, role);
		
		jc.addAddJobHandler(components);
		jc.addViewJobDetailHandler(components);
		return components.scene;
	}

	private void initializeAlert(ViewAllJobVar components) {
		components.alert = new Alert(AlertType.ERROR);
		components.alert.setTitle("Error");
	}

}
