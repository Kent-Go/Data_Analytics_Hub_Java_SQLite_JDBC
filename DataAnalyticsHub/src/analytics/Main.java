/*

 * Main.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */

package analytics;

import java.io.IOException;
import analytics.view.LoginViewer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * 
 * The Main class serves as the entry point for the Data Analytics Hub
 * application.
 */
public class Main extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    /**
     * The overridden method to start Data Analytics Hub application
     * 
     * @throws IOException The Input/Output exception to be throw when
     *                     LoginView.fxml is failed to load
     */
    @Override
    public void start(Stage primaryStage) {
	LoginViewer loginViewer = new LoginViewer();

	loginViewer.setPrimaryStage(primaryStage);

	primaryStage.setTitle(loginViewer.getTitle());
	try {
	    primaryStage.setScene(loginViewer.getScene());
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
	    fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}
	primaryStage.setResizable(false);
	
	primaryStage.show();

	/* Set primaryStage at the center of the screen */
	Rectangle2D screenVisualBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setY((screenVisualBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.setX((screenVisualBounds.getWidth() - primaryStage.getWidth()) / 2);
    }

}
