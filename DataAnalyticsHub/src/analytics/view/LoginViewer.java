package analytics.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class LoginViewer {
    public String getTitle() {
	return "Login";
    }

    public Scene getScene() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
	Pane pane = loader.load();
	Scene scene = new Scene(pane);
	return scene;
    }
}