package analytics.view;

import java.io.IOException;

import analytics.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginViewer {
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage primaryStage) {
   	this.primaryStage = primaryStage;
    }

    public String getTitle() {
	return "Login";
    }

    public Scene getScene() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
	Pane pane = loader.load();
	LoginController loginController = loader.getController();
	loginController.setPrimaryStage(primaryStage);
	Scene scene = new Scene(pane);
	return scene;
    }
    
}