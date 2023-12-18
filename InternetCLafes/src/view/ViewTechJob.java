package view;

import controller.JobController;
import controller.MenuController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import model.User;

public class ViewTechJob {

	// isi classnya tabel isinya semua staff job (view all jobs)
	// di halaman ini juga bisa add job
	// kalau select row, datanya akan ditampil ke window baru untuk update
	
	/*
	 * ViewAllJobVar berisi semua komponen UI yang digunakan di page ViewAllJob
	 */
	public class ViewTechJobVar{
		Scene scene;

		BorderPane bp;

		VBox vb;
		Label titleLbl;

		ScrollPane sp;
		GridPane gp;
		
		public TableView<Job> jobTable;
		TableColumn<Job, Integer> Job_IDCol;
		TableColumn<Job, Integer> PC_IDCol;
		TableColumn<Job, String> JobStatusCol;
		
		public Alert alert;
	}

	/*
	 * Initialize akan menginisialisasi semua komponen UI yang terdapat di page ini, termasuk menginisialisasi tabel dan mengatur style dari page.
	 */
	private void initialize(ViewTechJobVar components) {
		components.titleLbl = new Label("View Assigned Technician Jobs");

		initializeTable(components);

		components.sp = new ScrollPane();
		components.sp.setContent(components.jobTable);
		components.sp.setFitToWidth(true);
		
		components.vb = new VBox();
		components.vb.getChildren().addAll(components.sp);
		
		components.bp = new BorderPane();
		components.bp.setCenter(components.titleLbl);
		components.bp.setBottom(components.vb);
		
		components.scene = new Scene(components.bp);
	}

	/*
	 * setStyle digunakan untuk menambahkan styling ke komponen-komponen UI page.
	 */
	private void setStyle(ViewTechJobVar components) {
		components.titleLbl.setPadding(new Insets(20));
		
		// atur ukuran lebar setiap kolom menggunakan binding
		components.Job_IDCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(3).multiply(1));
		components.JobStatusCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(3).multiply(1));
		components.PC_IDCol.prefWidthProperty().bind(components.jobTable.widthProperty().divide(3).multiply(1));
		
		components.vb.setPadding(new Insets(20));
		components.vb.setSpacing(30);
		
	}

	/*
	 * initializeTable digunakan untuk menginisialisasi tabel, kolom-kolomnya juga
	 */
	private void initializeTable(ViewTechJobVar components) {
		components.jobTable = new TableView<>();

		components.Job_IDCol = new TableColumn<>("Job ID");
		components.JobStatusCol = new TableColumn<>("Job Status");
		components.PC_IDCol = new TableColumn<>("PC ID");

		components.jobTable.getColumns().add(components.Job_IDCol);
		components.jobTable.getColumns().add(components.JobStatusCol);
		components.jobTable.getColumns().add(components.PC_IDCol);
		
		components.Job_IDCol.setCellValueFactory(new PropertyValueFactory<>("Job_ID"));
		components.JobStatusCol.setCellValueFactory(new PropertyValueFactory<>("JobStatus"));
		components.PC_IDCol.setCellValueFactory(new PropertyValueFactory<>("PC_ID"));
		
	}

	/*
	 * initPage akan dipanggil oleh changeScene pada PageController untuk menggantikan isi dari window / scene menjadi current page.
	 */
	public Scene initPage(User user) {
		JobController jc = JobController.getInstance();
		MenuController mc = MenuController.getInstance();
		
		ViewTechJobVar components = new ViewTechJobVar();
		initialize(components);
		jc.getTableDataTech(components, user.getUserID());
		
		setStyle(components);
		initializeAlert(components);
		mc.selectMenuType(components.bp, user.getUserRole());
		
		jc.addViewTechJobDetailHandler(components);
		return components.scene;
	}

	private void initializeAlert(ViewTechJobVar components) {
		components.alert = new Alert(AlertType.ERROR);
		components.alert.setTitle("Error");
	}

}
