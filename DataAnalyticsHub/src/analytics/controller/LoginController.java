/*

 * LoginController.java
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
import analytics.model.User;
import analytics.model.UserModel;
import analytics.view.DashboardViewer;
import analytics.view.SignUpViewer;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * The LoginController class serves as the controller for the Login logic in
 * Data Analytics Hub application.
 */
public class LoginController {

    private Stage primaryStage;

    @FXML
    private TextField usernameInputField;
    @FXML
    private TextField passwordInputField;

    public LoginController() {
    }

    /**
     * The method to set private primaryStage variable
     * 
     * @param primaryStage The Stage object to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
    }

    /**
     * The method to handle user login. It verifies username and password inputs.
     * 
     * @param event The ActionEvent object which indicates that Login button-clicked
     *              action occurred
     */
    @FXML
    public void loginUserHandler(ActionEvent event) {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();

	try {
	    checkInputEmpty(username);
	    checkInputEmpty(password);
	    User loginUser = UserModel.getInstance().verifyUser(username,
		    password); /* verfiy if username and password are validate using SQLite Database */

	    DashboardViewer dashboardViewer = new DashboardViewer();
	    dashboardViewer.setPrimaryStage(primaryStage);
	    primaryStage.setTitle(dashboardViewer.getTitle());
	    primaryStage.setScene(dashboardViewer.getScene(loginUser));
	    primaryStage.setResizable(false);
	} catch (EmptyInputException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Login Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	} catch (UserVerificationFailException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Login Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	} catch (IOException e) {
	    e.printStackTrace();
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
	    fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}
    }

    /**
     * The method to redirect user to sign up scene.
     * 
     * @param event The ActionEvent object which indicates that Sign Up
     *              button-clicked action occurred
     */
    @FXML
    public void redirectSignUpHandler(ActionEvent event) {
	SignUpViewer signUpViewer = new SignUpViewer();

	signUpViewer.setPrimaryStage(primaryStage);
	primaryStage.setTitle(signUpViewer.getTitle());

	try {
	    primaryStage.setScene(signUpViewer.getScene());
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading SignUpView.fxml");
	    fileLoadingErrorAlert.setContentText("SignUpView.fxml file path is not found");
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
}