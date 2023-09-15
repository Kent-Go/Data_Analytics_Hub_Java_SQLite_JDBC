package analytics.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginViewGUIFX {
    public String getTitle() {
	return "Data Analytics Hub Login";
    }

    public Scene getScene() {
	Label usernameLabel = new Label("Username:");
	Label passwordLabel = new Label("Password:");
	Label registerLabel = new Label("Don't have an account?");
	registerLabel.setMinWidth(100);

	TextField usernameInputField = new TextField();
	TextField passwordInputField = new TextField();

	Button loginButton = new Button("Login");
	Button registerButton = new Button("Register");

	GridPane gridPane = new GridPane();
	gridPane.setPadding(new Insets(10, 10, 10, 10));
	gridPane.setHgap(10);
	gridPane.setVgap(10);

	// add Label to gridpane
	gridPane.add(usernameLabel, 0, 0); // (col, row)
	gridPane.add(passwordLabel, 0, 1);

	// add textfield to gridpane
	gridPane.add(usernameInputField, 1, 0);
	gridPane.add(passwordInputField, 1, 1);

	gridPane.add(loginButton, 1, 3);

	gridPane.add(registerLabel, 0, 4);
	gridPane.add(registerButton, 1, 4);

	// create sccene object and pass gridpane to Scene constructor
	Scene scene = new Scene(gridPane);

	// return Scene
	return scene;
    }
}
