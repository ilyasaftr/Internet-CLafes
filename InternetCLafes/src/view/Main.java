package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	// Class ini hanya dibuat untuk testing
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new ViewAllReport(primaryStage);
	}

}
