package analytics.view;

import analytics.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    private User loginUser;
    @FXML
    private Label welcomeMessage;
    
    public DashboardController() {
    }
    
    public void setLoginUser(User loginUser) {
	this.loginUser = loginUser;
    }
    
    public void displayWelcomeMessage(String firstName, String lastName) {
	welcomeMessage.setText(String.format("Welcome, %s %s", firstName, lastName));
    }
}
