package analytics.controller;

import analytics.UsernameExistedException;
import analytics.model.Database;
import analytics.model.User;
import analytics.view.DashboardViewer;
import analytics.EmptyInputException;
import analytics.InvalidPasswordLengthException;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
    }
    
    public void initaliseUser(User loginUser) {
	this.loginUser = loginUser;
	setTextField();;
    }
    
    private void setTextField() {
	usernameInputField.setText(loginUser.getUsername());
	passwordInputField.setText(loginUser.getPassword());
	firstNameInputField.setText(loginUser.getFirstName());
	lastNameInputField.setText(loginUser.getLastName());
    }
    
    @FXML
    public void saveProfileEditHandler(ActionEvent event) {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();
	String firstName = firstNameInputField.getText();
	String lastName = lastNameInputField.getText();

	Database dataBase = new Database();

	try {
	    checkInputEmpty(username);
	    checkInputEmpty(password);
	    checkInputEmpty(firstName);
	    checkInputEmpty(lastName);
	    
	    if (!username.equals(loginUser.getUsername())) {
		dataBase.checkUserExist(username);
	    }

	    checkPasswordLength(password);
	    
	    this.updatedUser = new User(username, password, firstName, lastName, 0);
	    dataBase.updateUser(loginUser, updatedUser);
	    Alert loginFailedAlert = new Alert(AlertType.INFORMATION);
	    loginFailedAlert.setHeaderText("Edit Profile Success. Your new user profile is now saved.");
	    loginFailedAlert.setContentText("Click OK to go back to dashboard.");
	    loginFailedAlert.showAndWait();
	    redirectDashboardPageHandler(event);
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
    
    public void cancelEditProfileHandler (ActionEvent event) {
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
    
    private void checkPasswordLength(String input) throws InvalidPasswordLengthException {
	if (input.length() < 6) {
	    throw new InvalidPasswordLengthException();
	}
    }
}