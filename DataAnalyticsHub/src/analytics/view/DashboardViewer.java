package analytics.view;

import java.io.IOException;

import analytics.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class DashboardViewer {
    
    private User loginUser;
    
    public DashboardViewer(User loginUser) {
	this.loginUser = loginUser;
    }
    
    public String getTitle() {
	return "Dashboard";
    }

    public Scene getScene() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
	Pane pane = loader.load();
	
	DashboardController dashboardController = loader.getController();
	dashboardController.setLoginUser(loginUser);
	dashboardController.displayWelcomeMessage(loginUser.getFirstName(), loginUser.getLastName());
	
	Scene scene = new Scene(pane);
	return scene;
    }
}
