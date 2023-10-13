/*

 * SignUpController.java
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

import analytics.view.LoginViewer;
import analytics.model.UserModel;
import analytics.model.exceptions.EmptyInputException;
import analytics.model.exceptions.InvalidPasswordLengthException;
import analytics.model.exceptions.UsernameExistedException;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * The SignUpController class serves as the controller for the Sign Up logic in
 * Data Analytics Hub application.
 */
public class SignUpController {

    private Stage primaryStage;

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
     * The method to handle user login.
     * 
     * @param event The ActionEvent object which indicates that Sign Up
     *              button-clicked action occurred
     */
    @FXML
    public void signUpUserHandler(ActionEvent event) {
	try {
	    UserModel.getInstance().createUser(usernameInputField.getText(), passwordInputField.getText(),
		    firstNameInputField.getText(),
		    lastNameInputField.getText()); /* create new user profile record in SQLite Database */

	    Alert SignUpSuccessAlert = AlertPopUp.getInstance()
		    .showInfoAlert("Sign Up Success. Your user profile is created.", "Click OK to proceed to login.");
	    SignUpSuccessAlert.showAndWait();

	    redirectLoginPageHandler(event); /* redirect the user to login scene */
	} catch (EmptyInputException e) {
	    Alert signUpFailedAlert = AlertPopUp.getInstance().showErrorAlert("Sign Up Failed", e.getMessage());
	    signUpFailedAlert.show();
	} catch (UsernameExistedException e) {
	    Alert signUpFailedAlert = AlertPopUp.getInstance().showErrorAlert("Sign Up Failed", e.getMessage());
	    signUpFailedAlert.show();
	} catch (InvalidPasswordLengthException e) {
	    Alert signUpFailedAlert = AlertPopUp.getInstance().showErrorAlert("Sign Up Failed", e.getMessage());
	    signUpFailedAlert.show();
	}
    }

    /**
     * The method to redirect user to sign in scene.
     * 
     * @param event The ActionEvent object which indicates that Login button-clicked
     *              action occurred
     */
    @FXML
    public void redirectLoginPageHandler(ActionEvent event) {
	LoginViewer loginViewer = new LoginViewer();

	loginViewer.setPrimaryStage(primaryStage);
	primaryStage.setTitle(loginViewer.getTitle());

	try {
	    primaryStage.setScene(loginViewer.getScene());
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = AlertPopUp.getInstance().showErrorAlert("Fail loading LoginView.fxml",
		    "LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}

	primaryStage.setResizable(false);
    }
}