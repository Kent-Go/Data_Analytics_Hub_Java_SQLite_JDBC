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
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
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
	try {
	    User loginUser = UserModel.getInstance().verifyUser(usernameInputField.getText(), passwordInputField
		    .getText()); /* verify if username and password are validate using SQLite Database */

	    DashboardViewer dashboardViewer = new DashboardViewer();
	    dashboardViewer.setPrimaryStage(primaryStage);
	    primaryStage.setTitle(dashboardViewer.getTitle());
	    primaryStage.setScene(dashboardViewer.getScene(loginUser));
	    primaryStage.setResizable(false);

	    /* Set primaryStage at the center of the screen */
	    Rectangle2D screenVisualBounds = Screen.getPrimary().getVisualBounds();
	    primaryStage.setY((screenVisualBounds.getHeight() - primaryStage.getHeight()) / 2);
	    primaryStage.setX((screenVisualBounds.getWidth() - primaryStage.getWidth()) / 2);

	} catch (EmptyInputException e) {
	    Alert loginFailedAlert = AlertPopUp.getInstance().showErrorAlert("Login Failed", e.getMessage());
	    loginFailedAlert.initOwner(primaryStage);
	    loginFailedAlert.show();
	} catch (UserVerificationFailException e) {
	    Alert loginFailedAlert = AlertPopUp.getInstance().showErrorAlert("Login Failed", e.getMessage());
	    loginFailedAlert.initOwner(primaryStage);
	    loginFailedAlert.show();
	} catch (IOException e) {
	    Alert loginFailedAlert = AlertPopUp.getInstance().showErrorAlert("Login Failed", e.getMessage());
	    loginFailedAlert.initOwner(primaryStage);
	    loginFailedAlert.show();
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
	try {
	    SignUpViewer signUpViewer = new SignUpViewer();
	    signUpViewer.setPrimaryStage(primaryStage);
	    primaryStage.setTitle(signUpViewer.getTitle());
	    primaryStage.setScene(signUpViewer.getScene());
	    primaryStage.setResizable(false);
	    /* Set primaryStage at the center of the screen */
	    Rectangle2D screenVisualBounds = Screen.getPrimary().getVisualBounds();
	    primaryStage.setY((screenVisualBounds.getHeight() - primaryStage.getHeight()) / 2);
	    primaryStage.setX((screenVisualBounds.getWidth() - primaryStage.getWidth()) / 2);
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = AlertPopUp.getInstance().showErrorAlert("Fail loading SignUpView.fxml",
		    "SignUpView.fxml file path is not found");
	    fileLoadingErrorAlert.initOwner(primaryStage);
	    fileLoadingErrorAlert.show();
	}

    }
}