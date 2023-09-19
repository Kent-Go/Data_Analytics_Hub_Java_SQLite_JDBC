package analytics.view;

import analytics.EmptyInputException;
import analytics.UserVerificationFailException;
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

public class LoginController {

    private Stage primaryStage;

    @FXML
    private TextField usernameInputField;
    @FXML
    private TextField passwordInputField;

    public void loginUser(ActionEvent event) {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();

	Database dataBase = new Database();

	try {
	    checkInputEmpty(username);
	    checkInputEmpty(password);
	    dataBase.verifyUser(username, password);
	    User loginUser = dataBase.retrieveUser(username);
	    DashboardViewer dashboardViewer = new DashboardViewer();
	    primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    public void redirectSignUpPage(ActionEvent event) {
	SignUpViewer signUpViewer = new SignUpViewer();

	primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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