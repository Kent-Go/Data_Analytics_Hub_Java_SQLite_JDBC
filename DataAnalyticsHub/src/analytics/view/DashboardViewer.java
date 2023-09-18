package analytics.view;

import java.io.IOException;

import analytics.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class DashboardViewer {
    
    public String getTitle() {
	return "Dashboard";
    }

    public Scene getScene(User loginUser) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
	
	Pane pane = loader.load();
	DashboardController dashboardController = loader.getController();
	dashboardController.initaliseUser(loginUser);
	dashboardController.displayWelcomeMessage();
	
	Scene scene = new Scene(pane);
	return scene;
    }
    
}
