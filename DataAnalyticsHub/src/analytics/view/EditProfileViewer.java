package analytics.view;

import java.io.IOException;

import analytics.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class EditProfileViewer {
            
    public String getTitle() {
	return "Edit Profile";
    }

    public Scene getScene(User loginUser) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfileView.fxml"));
	
	Pane pane = loader.load();
	EditProfileController editProfileController = loader.getController();
	editProfileController.initaliseUser(loginUser);
	
	Scene scene = new Scene(pane);
	return scene;
    }
}
