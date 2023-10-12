/*

 * EditProfileViewer.java
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

import analytics.controller.EditProfileController;
import analytics.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * The EditProfileViewer class serves as the view for the Edit Profile interface
 * presentation in Data Analytics Hub application.
 */
public class EditProfileViewer {
    
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
     * @return "Edit Profile" The stage title
     */
    public String getTitle() {
	return "Edit Profile";
    }

    /**
     * The method to return stage scene
     * 
     * @param loginUser The current login user object
     * @return scene The Edit Profile scene
     */
    public Scene getScene(User loginUser) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfileView.fxml"));
	
	Pane pane = loader.load();
	EditProfileController editProfileController = loader.getController();
	editProfileController.initaliseUser(loginUser);
	editProfileController.setPrimaryStage(primaryStage);
	
	Scene scene = new Scene(pane);
	return scene;
    }
}
