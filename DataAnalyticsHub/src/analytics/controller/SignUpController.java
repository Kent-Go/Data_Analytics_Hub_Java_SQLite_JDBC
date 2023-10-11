package analytics.controller;

import analytics.model.User;
import analytics.view.LoginViewer;
import analytics.model.UserModel;
import analytics.model.exceptions.EmptyInputException;
import analytics.model.exceptions.InvalidPasswordLengthException;
import analytics.model.exceptions.UsernameExistedException;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
    }

    @FXML
    public void signUpUserHandler(ActionEvent event) {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();
	String firstName = firstNameInputField.getText();
	String lastName = lastNameInputField.getText();

	try {
	    checkInputEmpty(username);
	    checkInputEmpty(password);
	    checkInputEmpty(firstName);
	    checkInputEmpty(lastName);
	    UserModel.getInstance().checkUserExist(username);
	    checkPasswordLength(password);

	    UserModel.getInstance().createUser(new User(username, password, firstName, lastName, 0));
	    Alert loginFailedAlert = new Alert(AlertType.INFORMATION);
	    loginFailedAlert.setHeaderText("Sign Up Success. Your user profile is created.");
	    loginFailedAlert.setContentText("Click OK to proceed to login.");
	    loginFailedAlert.showAndWait();
	    redirectLoginPageHandler(event);
	} catch (EmptyInputException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Sign Up Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	} catch (UsernameExistedException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Sign Up Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	} catch (InvalidPasswordLengthException e) {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Sign Up Failed");
	    loginFailedAlert.setContentText(e.getMessage());
	    loginFailedAlert.show();
	}
    }

    @FXML
    public void redirectLoginPageHandler(ActionEvent event) {
	LoginViewer loginViewer = new LoginViewer();

	loginViewer.setPrimaryStage(primaryStage);
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