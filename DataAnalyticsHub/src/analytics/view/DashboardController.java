package analytics.view;

import java.io.IOException;

import analytics.EmptyInputException;
import analytics.InvalidPasswordLengthException;
import analytics.UsernameExistedException;
import analytics.model.Database;
import analytics.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DashboardController {

    private Stage primaryStage;

    private User loginUser;
    
    @FXML
    private Label welcomeMessage;

    public void initaliseUser(User loginUser) {
	this.loginUser = loginUser;
    }

    public void displayWelcomeMessage() {
	welcomeMessage.setText(String.format("Welcome, %s %s", loginUser.getFirstName(), loginUser.getLastName()));
    }

    public void redirectEditProfilePage(ActionEvent event) {
	try {
	    EditProfileViewer editProfileViewer = new EditProfileViewer();
	    primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    primaryStage.setTitle(editProfileViewer.getTitle());
	    primaryStage.setScene(editProfileViewer.getScene(loginUser));
	    primaryStage.setResizable(false);
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading EditProfileView.fxml");
	    fileLoadingErrorAlert.setContentText("EditProfileView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}
    }

    public void logOutUser(ActionEvent event) {
	Alert logOutSuccessAlert = new Alert(AlertType.INFORMATION);
	logOutSuccessAlert.setHeaderText("You are now logged out.");
	logOutSuccessAlert.setContentText("Click OK to proceed to login.");
	logOutSuccessAlert.showAndWait();
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
