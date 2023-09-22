package analytics.view;

import java.io.IOException;

import analytics.controller.EditProfileController;
import analytics.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditProfileViewer {
    
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
   	this.primaryStage = primaryStage;
    }
            
    public String getTitle() {
	return "Edit Profile";
    }

    public Scene getScene(User loginUser) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfileView.fxml"));
	
	Pane pane = loader.load();
	EditProfileController editProfileController = loader.getController();
	editProfileController.initaliseUser(loginUser);
	editProfileController.setPrimaryStage(primaryStage);
	
	Scene scene = new Scene(pane);
	return scene;
    }
}
