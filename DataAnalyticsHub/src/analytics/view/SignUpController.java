package analytics.view;

import analytics.model.Database;
import analytics.model.User;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    public void signUpUser(ActionEvent event) {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();
	String firstName = firstNameInputField.getText();
	String lastName = lastNameInputField.getText();

	Database dataBase = new Database();

	// handle empty input

	if (dataBase.checkUserExist(username)) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Sign Up Failed");
	    loginFailedAlert.setContentText("Username is already registered. Please try again.");
	    loginFailedAlert.show();
	    return;
	}

	// Handle email, password, first name and last name error

	dataBase.createUser(new User(username, password, firstName, lastName, 0));

	Alert loginFailedAlert = new Alert(AlertType.INFORMATION);
	loginFailedAlert.setHeaderText("Sign Up Success. Your user profile is created.");
	loginFailedAlert.setContentText("Click OK to proceed to login.");
	loginFailedAlert.showAndWait();

	redirectLoginPage(event);
    }

    public void redirectLoginPage(ActionEvent event) {
	LoginViewer loginViewer = new LoginViewer();

	primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	primaryStage.setTitle(loginViewer.getTitle());

	try {
	    primaryStage.setScene(loginViewer.getScene());
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
	    fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}

	primaryStage.setResizable(false);
    }
}