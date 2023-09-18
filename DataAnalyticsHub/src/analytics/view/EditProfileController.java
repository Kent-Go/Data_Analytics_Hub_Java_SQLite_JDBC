package analytics.view;

import analytics.UsernameExistedException;
import analytics.model.Database;
import analytics.model.User;
import analytics.EmptyInputException;
import analytics.InvalidPasswordLengthException;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public void initaliseUser(User loginUser) {
	this.loginUser = loginUser;
    }
    
    public void saveEdit(ActionEvent event) {
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
	    dataBase.checkUserExist(username);
	    checkPasswordLength(password);
	    
	    this.updatedUser = new User(username, password, firstName, lastName, 0);
	    dataBase.updateUser(loginUser, updatedUser);
	    Alert loginFailedAlert = new Alert(AlertType.INFORMATION);
	    loginFailedAlert.setHeaderText("Edit Profile Success. Your new user profile is now saved.");
	    loginFailedAlert.setContentText("Click OK to go back to dashboard.");
	    loginFailedAlert.showAndWait();
	    redirectDashboardPage(event);
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
    
    public void redirectDashboardPage(ActionEvent event) {
	DashboardViewer dashboardViewer = new DashboardViewer();

	primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    
    public void cancelEditProfile (ActionEvent event) {
	DashboardViewer dashboardViewer = new DashboardViewer();

	primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
	if (input.length() < 8) {
	    throw new InvalidPasswordLengthException();
	}
    }
}