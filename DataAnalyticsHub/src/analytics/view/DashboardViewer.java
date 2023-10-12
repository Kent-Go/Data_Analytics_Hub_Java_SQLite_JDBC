/*

 * DashboardViewer.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.view;

import java.io.IOException;

import analytics.controller.DashboardController;
import analytics.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * The DashboardViewer class serves as the view for the Dashboard interface
 * presentation in Data Analytics Hub application.
 */
public class DashboardViewer {

    private Stage primaryStage;

    /**
     * The method to set private primaryStage variable
     * 
     * @param primaryStage The Stage object to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
    }

    /**
     * The method to return stage title
     * 
     * @return "Dashboard" The stage title
     */
    public String getTitle() {
	return "Dashboard";
    }

    /**
     * The method to return stage scene
     * 
     * @param loginUser The current login user object
     * @return scene The Dashboard scene
     */
    public Scene getScene(User loginUser) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));

	Pane pane = loader.load();
	DashboardController dashboardController = loader.getController();
	dashboardController.initaliseUser(loginUser);
	dashboardController.setPrimaryStage(primaryStage);
	dashboardController.displayWelcomeMessage();

	Scene scene = new Scene(pane);
	return scene;
    }

}
