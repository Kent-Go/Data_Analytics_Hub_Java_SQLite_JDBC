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
     * The method to handle saving edited profile into SQLite Database. It validates
     * username, password, first name and last input inputs.
     * 
     * @param event The ActionEvent object which indicates that Save Edit
     *              button-clicked action occurred
     */
    @FXML
    public void saveProfileEditHandler(ActionEvent event) {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();
	String firstName = firstNameInputField.getText();
	String lastName = lastNameInputField.getText();

	try {
	    checkInputEmpty(username);
	    checkInputEmpty(password);
	    checkInputEmpty(firstName);
	    checkInputEmpty(lastName);

	    if (!username.equals(loginUser.getUsername())) { /* check if username exists in database */
		UserModel.getInstance().checkUserExist(username);
	    }

	    checkPasswordLength(password); /* check password input length */

	    this.updatedUser = new User(username, password, firstName, lastName, loginUser.getVip());
	    UserModel.getInstance().updateUser(loginUser, updatedUser);
	    PostModel.getInstance().updatePost(loginUser, updatedUser);
	    Alert loginFailedAlert = new Alert(AlertType.INFORMATION);
	    loginFailedAlert.setHeaderText("Edit Profile Success. Your new user profile is now saved.");
	    loginFailedAlert.setContentText("Click OK to go back to dashboard.");
	    loginFailedAlert.showAndWait();
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

    /**
     * The method to check if input is empty to throw user-defined
     * EmptyContentException
     * 
     * @param content The string to be validate
     */
    private void checkInputEmpty(String input) throws EmptyInputException {
	if (input.isEmpty()) {
	    throw new EmptyInputException();
	}
    }

    /**
     * The method to check if input did not exceed 6 character length to throw
     * user-defined InvalidPasswordLengthException
     * 
     * @param input The string to be check
     */
    private void checkPasswordLength(String input) throws InvalidPasswordLengthException {
	if (input.length() < 6) {
	    throw new InvalidPasswordLengthException();
	}
    }
}