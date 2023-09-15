package analytics.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
    
    private Stage primaryStage;

    @FXML
    private TextField usernameInputField;
    @FXML
    private TextField passwordInputField;
    
    
    public void loginUser(ActionEvent event) throws IOException {
	String username = usernameInputField.getText();
	String password = passwordInputField.getText();
	
	// verify username and password
	
	DashboardViewer dashboardViewer = new DashboardViewer();
	
	primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	primaryStage.setTitle(dashboardViewer.getTitle());
	try {
	    primaryStage.setScene(dashboardViewer.getScene());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	primaryStage.setResizable(false);
    }
}