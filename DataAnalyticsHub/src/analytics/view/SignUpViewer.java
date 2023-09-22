package analytics.view;

import java.io.IOException;

import analytics.controller.LoginController;
import analytics.controller.SignUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignUpViewer {
    
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
   	this.primaryStage = primaryStage;
    }
    
    public String getTitle() {
	return "Sign Up";
    }

    public Scene getScene() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpView.fxml"));
	Pane pane = loader.load();
	SignUpController signUpController = loader.getController();
	signUpController.setPrimaryStage(primaryStage);
	Scene scene = new Scene(pane);
	return scene;
    }
}