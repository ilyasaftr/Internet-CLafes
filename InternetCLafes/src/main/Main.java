package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.Register;

public class Main extends Application implements EventHandler<WindowEvent>  {
	/*
	 * Main class digunakan sebagai class inisialisasi program.
	 *
	 * Main adalah class yang memulai aplikasi di landing page, dalam kasus ini, page itu adalah Register.
	 *
	 * Main class menerapkan design pattern Singleton tepatnya pada stage karena dalam aplikasi Internet CLafes, yang berganti hanya scene atau isi dari window.
	*/

	/*
	 * Variabel stage dibuat static agar bisa dipanggil tanpa membuat instance Main baru,
	 * serta dibuat volatile agar semua thread dapat melihat perubahan yang terjadi pada variabel ini.
	*/
	public static volatile Stage stage;

	/*
	 * Method changeScene juga dibuat static agar bisa dipanggil tanpa membuat instance Main baru.
	 * Ditambahkan mekanisme Double Check Locking (Check - Lock - Check) agar semua thread yang memanggil process ini disusun dalam antrian supaya tidak tercipta lebih dari satu instance.
	 * Method ini bertujuan menggantikan scene / content yang akan ditampilkan pada stage / window.
	 */
	public static void changeScene(Scene s) {
		if(stage == null) {
			synchronized (Main.class) {
				if(stage == null) {
					 return;
				}
			}
		}

		stage.setScene(s);
	}

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * Method start menandakan awal dari aplikasi Internet CLafes.
	 * Dengan kata lain, dia menentukan landing page.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Main.changeScene(new Register().initPage());

		stage.setTitle("Internet CLafes");
		stage.show();
	}

	// kalau close di Main stage, semua window yang bersangkutan dengan app akan close
	@Override
	public void handle(WindowEvent event) {
		Platform.exit();
	}

}
