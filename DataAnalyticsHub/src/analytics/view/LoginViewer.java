/*

 * LoginViewer.java
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

import analytics.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * The LoginViewer class serves as the view for the Login interface
 * presentation in Data Analytics Hub application.
 */
public class LoginViewer {
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
     * @return "Login" The stage title
     */
    public String getTitle() {
	return "Login";
    }

    /**
     * The method to return stage scene
     * 
     * @return scene The Login scene
     */
    public Scene getScene() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
	Pane pane = loader.load();
	LoginController loginController = loader.getController();
	loginController.setPrimaryStage(primaryStage);
	Scene scene = new Scene(pane);
	return scene;
    }
    
}