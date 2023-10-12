/*

 * EditProfileController.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.controller;

import analytics.model.exceptions.*;
import analytics.model.PostModel;
import analytics.model.User;
import analytics.model.UserModel;
import analytics.view.DashboardViewer;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * The EditProfileController class serves as the controller for the Edit Profile
 * logic in Data Analytics Hub application.
 */
public class EditProfileController {

    private Stage primaryStage;

    private User loginUser;

    private User updatedUser;

    @FXML
    private TextField usernameInputField;
    @FXML
    private TextField passwordInputField;
    @FXML
    private TextField firstNameInputField;
    @FXML
    private TextField lastNameInputField;

    /**
     * The method to set private primaryStage variable
     * 
     * @param primaryStage The Stage object to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
    }

    /**
     * The method to initialize current login user
     * 
     * @param loginUser The User object to be initialize
     */
    public void initaliseUser(User loginUser) {
	this.loginUser = loginUser;
	setTextField();
	;
    }

    /**
     * The method to initialize username, passsword, first name and last name input
     * field with the current user's profile
     * 
     */
    private void setTextField() {
	usernameInputField.setText(loginUser.getUsername());
	passwordInputField.setText(loginUser.getPassword());
	firstNameInputField.setText(loginUser.getFirstName());
	lastNameInputField.setText(loginUser.getLastName());
    }

    /**
     * The method to handle saving edited profile into SQLite Database.
     * 
     * @param event The ActionEvent object which indicates that Save Edit
     *              button-clicked action occurred
     */
    @FXML
    public void saveProfileEditHandler(ActionEvent event) {

	try {
	    UserModel.getInstance().updateUser(loginUser, usernameInputField.getText(), passwordInputField.getText(),
		    firstNameInputField.getText(), lastNameInputField.getText()); /* Update user profile */

	    this.updatedUser = new User(usernameInputField.getText(), passwordInputField.getText(),
		    firstNameInputField.getText(), lastNameInputField.getText(), loginUser.getVip());

	    PostModel.getInstance().updatePost(loginUser, updatedUser); /* Update post's author */
	    
	    Alert EditProfileScucessAlert = new Alert(AlertType.INFORMATION);
	    EditProfileScucessAlert.setHeaderText("Edit Profile Success. Your new user profile is now saved.");
	    EditProfileScucessAlert.setContentText("Click OK to go back to dashboard.");
	    EditProfileScucessAlert.showAndWait();
	    
	    redirectDashboardPageHandler(event); /* redirect to dashboard */
	} catch (EmptyInputException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Edit Profile Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	} catch (UsernameExistedException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Edit Profile Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	} catch (InvalidPasswordLengthException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Edit Profile Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	}
    }

    /**
     * The method to redirect user to dashboard scene. This is called in
     * saveProfileEditHandler() after validate and saving edit profile.
     * 
     * @param event The ActionEvent object which indicates that Save Edit
     *              button-clicked action occurred
     */
    @FXML
    public void redirectDashboardPageHandler(ActionEvent event) {
	DashboardViewer dashboardViewer = new DashboardViewer();

	dashboardViewer.setPrimaryStage(primaryStage);
	primaryStage.setTitle(dashboardViewer.getTitle());

	try {
	    primaryStage.setScene(dashboardViewer.getScene(updatedUser));
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
	    fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}

	primaryStage.setResizable(false);
    }

    /**
     * The method to redirect user to dashboard scene. This is called only if the
     * user decide to not edit profile.
     * 
     * @param event The ActionEvent object which indicates that Cancel
     *              button-clicked action occurred
     */
    public void cancelEditProfileHandler(ActionEvent event) {
	DashboardViewer dashboardViewer = new DashboardViewer();

	dashboardViewer.setPrimaryStage(primaryStage);
	primaryStage.setTitle(dashboardViewer.getTitle());

	try {
	    primaryStage.setScene(dashboardViewer.getScene(loginUser));
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
	    fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}

	primaryStage.setResizable(false);
    }
}