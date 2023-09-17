package analytics.view;

import analytics.model.Database;
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

	if (dataBase.verifyUser(username, password)) {
	    DashboardViewer dashboardViewer = new DashboardViewer();

	    primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    primaryStage.setTitle(dashboardViewer.getTitle());

	    try {
		primaryStage.setScene(dashboardViewer.getScene());
	    } catch (IOException e) {
		Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
		fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
		fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
		fileLoadingErrorAlert.show();
	    }

	    primaryStage.setResizable(false);
	} else {
	    Alert loginFailedAlert = new Alert(AlertType.ERROR);
	    loginFailedAlert.setHeaderText("Login Failed");
	    loginFailedAlert.setContentText("Username and/or password is incorrect. Please try again.");
	    loginFailedAlert.show();
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
}