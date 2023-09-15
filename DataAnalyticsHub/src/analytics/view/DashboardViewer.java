package analytics.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class DashboardViewer {
    
    public String getTitle() {
	return "Dashboard";
    }

    public Scene getScene() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
	Pane pane = loader.load();
	Scene scene = new Scene(pane);
	return scene;
    }
}
