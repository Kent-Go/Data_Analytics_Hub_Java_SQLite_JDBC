package analytics;

import java.io.IOException;

import analytics.view.LoginViewer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
	LoginViewer loginViewer = new LoginViewer();

	primaryStage.setTitle(loginViewer.getTitle());
	try {
	    primaryStage.setScene(loginViewer.getScene());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	primaryStage.setResizable(false);
	primaryStage.show();
    }

}
