/*

l * DashboardController.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */

package analytics.controller;

import analytics.model.exceptions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Scanner;

import analytics.model.Post;
import analytics.model.PostModel;
import analytics.model.User;
import analytics.model.UserModel;
import analytics.view.EditProfileViewer;
import analytics.view.LoginViewer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 
 * The DashboardController class serves as the controller for the Dashboard
 * logic in Data Analytics Hub application.
 */
public class DashboardController {

    private Stage primaryStage;

    private User loginUser;

    @FXML
    private Label welcomeMessage;

    @FXML
    private TextField addPostIDInputField;

    @FXML
    private TextField addPostLikesInputField;

    @FXML
    private TextField addPostSharesInputField;

    @FXML
    private Label addPostAuthorLabelField;

    @FXML
    private TextField addPostContentInputField;

    @FXML
    private TextField addPostDateTimeInputField;

    @FXML
    private TextField retrievePostIDInputField;

    @FXML
    private TableView<Post> retrievePostTableView;
    @FXML
    private TableColumn<Post, Integer> retrievePostIDColumn;
    @FXML
    private TableColumn<Post, String> retrievePostContentColumn;
    @FXML
    private TableColumn<Post, String> retrievePostAuthorColumn;
    @FXML
    private TableColumn<Post, Integer> retrievePostLikesColumn;
    @FXML
    private TableColumn<Post, Integer> retrievePostSharesColumn;
    @FXML
    private TableColumn<Post, String> retrievePostDateTimeColumn;

    @FXML
    private TextField removePostIDInputField;

    @FXML
    private TextField retrieveTopNLikesPostNumberInputField;

    @FXML
    private ChoiceBox<String> retrieveTopNLikesPostAuthorChoiceBox;

    @FXML
    private Label NumPostExceedDatabaseLabel;

    @FXML
    private Label TopNLikesPostLabel;

    @FXML
    private TableView<Post> retrieveTopNLikesPostTableView;
    @FXML
    private TableColumn<Post, Integer> retrieveTopNLikesPostIDColumn;
    @FXML
    private TableColumn<Post, String> retrieveTopNLikesPostContentColumn;
    @FXML
    private TableColumn<Post, String> retrieveTopNLikesPostAuthorColumn;
    @FXML
    private TableColumn<Post, Integer> retrieveTopNLikesPostLikesColumn;
    @FXML
    private TableColumn<Post, Integer> retrieveTopNLikesPostSharesColumn;
    @FXML
    private TableColumn<Post, String> retrieveTopNLikesPostDateTimeColumn;

    @FXML
    private TextField exportPostIDInputField;

    @FXML
    private Button upgradeToVipButton;

    @FXML
    private TabPane dashboardTabPane;

    @FXML
    private Tab dataVisualizationTab;

    @FXML
    private Tab bulkImportPostTab;

    @FXML
    private PieChart PostSharesDistributionPieChart;

    /**
     * The method to set private primaryStage variable
     * 
     * @param primaryStage The Stage object to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
	this.primaryStage = primaryStage;
    }

    /**
     * The method to initialize current login user and to initialize the dashboard's
     * functionality based on vip status
     * 
     * @param loginUser The User object to be initialize
     */
    public void initaliseUser(User loginUser) {
	this.loginUser = loginUser;
	addPostAuthorLabelField.setText(this.loginUser.getUsername());

	ObservableList<String> authorList = UserModel.getInstance().retreieveAllUsersName();
	authorList.add("All Users");
	retrieveTopNLikesPostAuthorChoiceBox.setItems(authorList);

	if (this.loginUser.getVip() == 1) {
	    upgradeToVipButton.setVisible(false);
	} else {
	    dashboardTabPane.getTabs().remove(dataVisualizationTab);
	    dashboardTabPane.getTabs().remove(bulkImportPostTab);
	}
    }

    /**
     * The method to display welcome message using login user's first and last name
     * 
     */
    public void displayWelcomeMessage() {
	welcomeMessage.setText(String.format("Welcome, %s %s", loginUser.getFirstName(), loginUser.getLastName()));
    }

    /**
     * The method to redirect user to Edit Profile scene.
     * 
     * @param event The ActionEvent object which indicates that Edit Profile
     *              button-clicked action occurred
     */
    @FXML
    public void redirectEditProfilePageHandler(ActionEvent event) {
	try {
	    EditProfileViewer editProfileViewer = new EditProfileViewer();
	    editProfileViewer.setPrimaryStage(primaryStage);
	    primaryStage.setTitle(editProfileViewer.getTitle());
	    primaryStage.setScene(editProfileViewer.getScene(loginUser));
	    primaryStage.setResizable(false);
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading EditProfileView.fxml");
	    fileLoadingErrorAlert.setContentText("EditProfileView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}
    }

    /**
     * The method to upgrade user vip status.
     * 
     * @param event The ActionEvent object which indicates that Upgrade To VIP
     *              button-clicked action occurred
     */
    @FXML
    public void upgradeToVipHandler(ActionEvent event) {
	Alert upgradeVipConfirnmationAlert = new Alert(AlertType.CONFIRMATION);
	upgradeVipConfirnmationAlert.setHeaderText("Upgrade To VIP");
	upgradeVipConfirnmationAlert
		.setContentText("Would you like to subscribe to the application for a monthly fee of $0?");
	upgradeVipConfirnmationAlert.showAndWait().ifPresent(buttonClicked -> {
	    if (buttonClicked == ButtonType.OK) {
		UserModel.getInstance().upgradeUserToVip(loginUser);

		Alert upgradeVIPSuccessAlert = new Alert(AlertType.INFORMATION);
		upgradeVIPSuccessAlert.setHeaderText("You are now upgraded to VIP.");
		upgradeVIPSuccessAlert.setContentText("Please log out and log in again to access VIP functionalities.");
		upgradeVIPSuccessAlert.showAndWait();
		redirectLoginPage(event);
	    }
	});
    }

    /**
     * The method to log out user.
     * 
     * @param event The ActionEvent object which indicates that Logout
     *              button-clicked action occurred
     */
    @FXML
    public void logOutUserHandler(ActionEvent event) {
	Alert logOutSuccessAlert = new Alert(AlertType.INFORMATION);
	logOutSuccessAlert.setHeaderText("You are now logged out.");
	logOutSuccessAlert.setContentText("Click OK to proceed to login.");
	logOutSuccessAlert.showAndWait();
	redirectLoginPage(event); /* redirect user to login scene */
    }

    /**
     * The method to redirect user to Login scene.
     * 
     * @param event The ActionEvent object which passed from logOutUserHandler()
     */
    private void redirectLoginPage(ActionEvent event) {
	LoginViewer loginViewer = new LoginViewer();

	loginViewer.setPrimaryStage(primaryStage);
	primaryStage.setTitle(loginViewer.getTitle());

	try {
	    primaryStage.setScene(loginViewer.getScene());
	} catch (IOException e) {
	    Alert fileLoadingErrorAlert = new Alert(AlertType.ERROR);
	    fileLoadingErrorAlert.setHeaderText("Fail loading LoginView.fxml");
	    fileLoadingErrorAlert.setContentText("LoginView.fxml file path is not found");
	    fileLoadingErrorAlert.show();
	}

	primaryStage.setResizable(false);
    }

    /**
     * The method to add new post to SQLite Database
     * 
     * @param event The ActionEvent object which indicates that Add Post
     *              button-clicked action occurred
     */
    @FXML
    public void addPostHandler(ActionEvent event) {

	try {

	    PostModel.getInstance().createPost(addPostIDInputField.getText(), addPostContentInputField.getText(),
		    addPostAuthorLabelField.getText(), addPostLikesInputField.getText(),
		    addPostSharesInputField.getText(), addPostDateTimeInputField.getText());

	    Alert addPostSuccess = new Alert(AlertType.INFORMATION);
	    addPostSuccess.setHeaderText("Add Post Success. Your new post is now saved.");
	    addPostSuccess.setContentText("Click OK to go back to dashboard.");
	    addPostSuccess.showAndWait();

	    addPostIDInputField.setText("");
	    addPostLikesInputField.setText("");
	    addPostSharesInputField.setText("");
	    addPostContentInputField.setText("");
	    addPostDateTimeInputField.setText("");
	} catch (EmptyInputException inputEmptyError) {
	    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
	    inputEmptyErrorAlert.setHeaderText("Add Post Failed");
	    inputEmptyErrorAlert.setContentText(inputEmptyError.getMessage());
	    inputEmptyErrorAlert.show();
	} catch (ExistedPostIDException postIDExisted) {
	    Alert PostIDExistedAlert = new Alert(AlertType.ERROR);
	    PostIDExistedAlert.setHeaderText("Add Post Failed");
	    PostIDExistedAlert.setContentText(postIDExisted.getMessage());
	    PostIDExistedAlert.show();
	} catch (NumberFormatException numberFormatError) {
	    Alert numberFormatErrorAlert = new Alert(AlertType.ERROR);
	    numberFormatErrorAlert.setHeaderText("Add Post Failed");
	    numberFormatErrorAlert.setContentText("Input must be an integer value.");
	    numberFormatErrorAlert.show();
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
	    integerNegativeErrorAlert.setHeaderText("Add Post Failed");
	    integerNegativeErrorAlert.setContentText(integerNegativeError.getMessage());
	    integerNegativeErrorAlert.show();
	} catch (InvalidContentException contentFormatError) {
	    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
	    integerNegativeErrorAlert.setHeaderText("Add Post Failed");
	    integerNegativeErrorAlert.setContentText(contentFormatError.getMessage());
	    integerNegativeErrorAlert.show();
	} catch (ParseException parseError) {
	    Alert parseErrorAlert = new Alert(AlertType.ERROR);
	    parseErrorAlert.setHeaderText("Add Post Failed");
	    parseErrorAlert.setContentText(
		    "Invalid date-time value or/and format. The date-time must be in the format of DD/MM/YYYY HH:MM.");
	    parseErrorAlert.show();
	}
    }

    /**
     * The method to retrieve a post from SQLite Database based on given post's ID
     * 
     * @param event The ActionEvent object which indicates that Retrieve
     *              button-clicked action occurred
     */
    @FXML
    public void retrievePostHandler(ActionEvent event) {
	try {
	    retrievePostTableView.getItems().clear();

	    Post post = PostModel.getInstance().validateInputRetrievePostID(retrievePostIDInputField.getText());

	    if (post != null) {
		retrievePostTableView.getItems().add(post);
		retrievePostIDColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("id"));
		retrievePostContentColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("content"));
		retrievePostAuthorColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("author"));
		retrievePostLikesColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("likes"));
		retrievePostSharesColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("shares"));
		retrievePostDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("dateTime"));
		retrievePostIDInputField.setText("");
	    } else {
		Alert postNotExistAlert = new Alert(AlertType.ERROR);
		postNotExistAlert.setHeaderText("Retreive Post Failed");
		postNotExistAlert.setContentText("Sorry the post does not exist in the database!");
		postNotExistAlert.show();
	    }
	} catch (EmptyInputException inputEmptyError) {
	    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
	    inputEmptyErrorAlert.setHeaderText("Retreive Post Failed");
	    inputEmptyErrorAlert.setContentText(inputEmptyError.getMessage());
	    inputEmptyErrorAlert.show();
	} catch (NumberFormatException numberFormatError) {
	    Alert numberFormatErrorAlert = new Alert(AlertType.ERROR);
	    numberFormatErrorAlert.setHeaderText("Retreive Post Failed");
	    numberFormatErrorAlert.setContentText("Input must be an integer value.");
	    numberFormatErrorAlert.show();
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
	    integerNegativeErrorAlert.setHeaderText("Retreive Post Failed");
	    integerNegativeErrorAlert.setContentText(integerNegativeError.getMessage());
	    integerNegativeErrorAlert.show();
	}
    }

    /**
     * The method to remove a post from SQLite Database based on given post's ID
     * 
     * @param event The ActionEvent object which indicates that Remove
     *              button-clicked action occurred
     */
    @FXML
    public void removePostHandler(ActionEvent event) {
	try {

	    Post post = PostModel.getInstance().removePost(removePostIDInputField.getText());

	    if (post != null) {
		Alert removePostSuccess = new Alert(AlertType.INFORMATION);
		removePostSuccess.setHeaderText("Remove Post Success");
		removePostSuccess.setContentText("The post is successfully removed from the database!");
		removePostSuccess.showAndWait();
		removePostIDInputField.setText("");
	    } else {
		Alert postNotExistAlert = new Alert(AlertType.ERROR);
		postNotExistAlert.setHeaderText("Remove Post Failed");
		postNotExistAlert.setContentText("Sorry the post does not exist in the database!");
		postNotExistAlert.show();
	    }
	} catch (EmptyInputException inputEmptyError) {
	    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
	    inputEmptyErrorAlert.setHeaderText("Remove Post Failed");
	    inputEmptyErrorAlert.setContentText(inputEmptyError.getMessage());
	    inputEmptyErrorAlert.show();
	} catch (NumberFormatException numberFormatError) {
	    Alert numberFormatErrorAlert = new Alert(AlertType.ERROR);
	    numberFormatErrorAlert.setHeaderText("Remove Post Failed");
	    numberFormatErrorAlert.setContentText("Input must be an integer value.");
	    numberFormatErrorAlert.show();
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
	    integerNegativeErrorAlert.setHeaderText("Remove Post Failed");
	    integerNegativeErrorAlert.setContentText(integerNegativeError.getMessage());
	    integerNegativeErrorAlert.show();
	}
    }

    /**
     * The method to retrieve Top N Like Post from SQLite Database based on N input
     * and chosen author
     * 
     * @param event The ActionEvent object which indicates that Retrieve
     *              button-clicked action occurred
     */
    @FXML
    public void retrieveTopNLikesPostHandler(ActionEvent event) {
	try {
	    retrieveTopNLikesPostTableView.getItems().clear();
	    NumPostExceedDatabaseLabel.setText("");
	    TopNLikesPostLabel.setText("");

	    String strNumberPost = retrieveTopNLikesPostNumberInputField.getText();
	    String selectedAuthor = retrieveTopNLikesPostAuthorChoiceBox.getValue();
	    int intNumberPost = 0;

	    if (selectedAuthor != null) {
		PriorityQueue<Post> topNLikesPost = PostModel.getInstance().retrieveTopNLikesPost(strNumberPost,
			selectedAuthor);

		if (topNLikesPost.size() == 0) {
		    NumPostExceedDatabaseLabel
			    .setText(String.format("0 post exist in the database for %s.", selectedAuthor));
		} else {
		    intNumberPost = Integer.parseInt(strNumberPost);
		    if (topNLikesPost.size() < intNumberPost) {
			intNumberPost = topNLikesPost.size();
			NumPostExceedDatabaseLabel.setText(
				String.format("Only %d posts exist in the database for %s. Showing all of them.",
					intNumberPost, selectedAuthor));
		    }
		    TopNLikesPostLabel
			    .setText(String.format("The top-%d posts with the most likes are:", intNumberPost));
		}

		int i = 0;
		while ((!topNLikesPost.isEmpty()) && (i < intNumberPost)) {
		    retrieveTopNLikesPostTableView.getItems().add(topNLikesPost.poll());
		    i++;
		}
		retrieveTopNLikesPostIDColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("id"));
		retrieveTopNLikesPostContentColumn
			.setCellValueFactory(new PropertyValueFactory<Post, String>("content"));
		retrieveTopNLikesPostAuthorColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("author"));
		retrieveTopNLikesPostLikesColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("likes"));
		retrieveTopNLikesPostSharesColumn
			.setCellValueFactory(new PropertyValueFactory<Post, Integer>("shares"));
		retrieveTopNLikesPostDateTimeColumn
			.setCellValueFactory(new PropertyValueFactory<Post, String>("dateTime"));

		retrieveTopNLikesPostNumberInputField.setText("");
		retrieveTopNLikesPostAuthorChoiceBox.setValue(null);

		;
	    } else {
		Alert selectedAuthorChoiceEmptyErrorAlert = new Alert(AlertType.ERROR);
		selectedAuthorChoiceEmptyErrorAlert.setHeaderText("Retreive Top N Likes Post Failed");
		selectedAuthorChoiceEmptyErrorAlert.setContentText("Please select an author!");
		selectedAuthorChoiceEmptyErrorAlert.show();
	    }
	} catch (EmptyInputException inputEmptyError) {
	    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
	    inputEmptyErrorAlert.setHeaderText("Retreive Top N Likes Post Failed");
	    inputEmptyErrorAlert.setContentText(inputEmptyError.getMessage());
	    inputEmptyErrorAlert.show();
	} catch (NumberFormatException numberFormatError) {
	    Alert numberFormatErrorAlert = new Alert(AlertType.ERROR);
	    numberFormatErrorAlert.setHeaderText("Retreive Top N Likes Post Failed");
	    numberFormatErrorAlert.setContentText("Input must be an integer value.");
	    numberFormatErrorAlert.show();
	} catch (InvalidNonPositiveIntegerException integerNonPositiveError) {
	    Alert integerNonPositiveErrorAlert = new Alert(AlertType.ERROR);
	    integerNonPositiveErrorAlert.setHeaderText("Retreive Top N Likes Post Failed");
	    integerNonPositiveErrorAlert.setContentText(integerNonPositiveError.getMessage());
	    integerNonPositiveErrorAlert.show();
	}
    }

    /**
     * The method to export a post from SQLite Database based on post's ID to .csv
     * file
     * 
     * @param event The ActionEvent object which indicates that Export
     *              button-clicked action occurred
     */
    @FXML
    public void exportPostHandler(ActionEvent event) {
	try {
	    String id = exportPostIDInputField.getText();

	    Post post = PostModel.getInstance().validateInputRetrievePostID(id);

	    if (post != null) {
		try {
		    FileChooser fileChooser = new FileChooser();
		    ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV file (*.csv)", "*.csv");
		    fileChooser.getExtensionFilters().add(extFilter);

		    File selectedFile = fileChooser.showSaveDialog(primaryStage);
		    if (selectedFile != null) {
			PrintWriter printWriter = new PrintWriter(selectedFile);
			printWriter.println("ID,content,author,likes,shares,date-time");
			printWriter.printf("%d,%s,%s,%d,%d,%s", post.getId(), post.getContent(), post.getAuthor(),
				post.getLikes(), post.getShares(), post.getDateTime());
			printWriter.close();

			Alert exportPostSuccess = new Alert(AlertType.INFORMATION);
			exportPostSuccess.setHeaderText("Export Post Success");
			exportPostSuccess.setContentText("The post is successfully exported into .csv file!");
			exportPostSuccess.showAndWait();
			exportPostIDInputField.setText("");
		    }
		} catch (FileNotFoundException fileNotFoundError) {
		    Alert fileNotFoundErrorAlert = new Alert(AlertType.ERROR);
		    fileNotFoundErrorAlert.setHeaderText("Export Post Failed");
		    fileNotFoundErrorAlert.setContentText("The file path is not found!");
		    fileNotFoundErrorAlert.show();
		}
	    } else {
		Alert postNotExistAlert = new Alert(AlertType.ERROR);
		postNotExistAlert.setHeaderText("Export Post Failed");
		postNotExistAlert.setContentText("Sorry the post does not exist in the database!");
		postNotExistAlert.show();
	    }
	} catch (EmptyInputException inputEmptyError) {
	    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
	    inputEmptyErrorAlert.setHeaderText("Export Post Failed");
	    inputEmptyErrorAlert.setContentText(inputEmptyError.getMessage());
	    inputEmptyErrorAlert.show();
	} catch (NumberFormatException numberFormatError) {
	    Alert numberFormatErrorAlert = new Alert(AlertType.ERROR);
	    numberFormatErrorAlert.setHeaderText("Export Post Failed");
	    numberFormatErrorAlert.setContentText("Input must be an integer value.");
	    numberFormatErrorAlert.show();
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
	    integerNegativeErrorAlert.setHeaderText("Export Post Failed");
	    integerNegativeErrorAlert.setContentText(integerNegativeError.getMessage());
	    integerNegativeErrorAlert.show();
	}
    }

    /**
     * The method to generate a post shares distribution pie chart from SQLite
     * Database
     * 
     * @param event The ActionEvent object which indicates that Generate
     *              button-clicked action occurred
     */
    @FXML
    public void generatePieChartHandler(ActionEvent event) {
	// Retrieve Post Share Distribution data
	ObservableList<PieChart.Data> pieChartData = PostModel.getInstance().retrievePostSharePieChart();
	// Display Pie Chart for Post Share Distribution
	PostSharesDistributionPieChart.setData(pieChartData);
    }

    /**
     * The method to bulk import multiple posts data from .csv file into SQLite
     * Database
     * 
     * @param event The ActionEvent object which indicates that Import
     *              button-clicked action occurred
     */
    @FXML
    public void bulkImportPostHandler(ActionEvent event) {
	FileChooser fileChooser = new FileChooser();

	ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV file (*.csv)", "*.csv");

	fileChooser.getExtensionFilters().add(extFilter);

	File selectedFile = fileChooser.showOpenDialog(primaryStage);

	int postImportSuccess = 0;

	// Convert file to an input stream
	try {
	    FileInputStream inputStream = new FileInputStream(selectedFile);

	    Scanner scanner = new Scanner(inputStream);

	    scanner.nextLine(); /* Bypass header. */

	    while (scanner.hasNextLine()) {
		String fileRow = scanner.nextLine();

		String[] postRow = fileRow.split(","); /* Split the row into tokens with "," as separator. */

		try {
		    PostModel.getInstance().createPost(postRow[0], postRow[1], postRow[2], postRow[3], postRow[4],
			    postRow[5]);
		    postImportSuccess += 1; /* Increment postImportSuccess if import post successful. */

		} catch (EmptyInputException inputEmptyError) {
		    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
		    inputEmptyErrorAlert.setHeaderText("Import Post Failed");
		    inputEmptyErrorAlert
			    .setContentText(inputEmptyError.getMessage() + "\nClick OK to continue to next post.");
		    inputEmptyErrorAlert.showAndWait();
		} catch (NumberFormatException numberFormatError) {
		    Alert numberFormatErrorAlert = new Alert(AlertType.ERROR);
		    numberFormatErrorAlert.setHeaderText("Import Post Failed");
		    numberFormatErrorAlert
			    .setContentText("Input must be an integer value." + "\nClick OK to continue to next post.");
		    numberFormatErrorAlert.showAndWait();
		} catch (ExistedPostIDException postIDExisted) {
		    Alert PostIDExistedAlert = new Alert(AlertType.ERROR);
		    PostIDExistedAlert.setHeaderText("Import Post Failed");
		    PostIDExistedAlert
			    .setContentText(postIDExisted.getMessage() + "\nClick OK to continue to next post.");
		    PostIDExistedAlert.showAndWait();
		} catch (InvalidNegativeIntegerException integerNegativeError) {
		    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
		    integerNegativeErrorAlert.setHeaderText("Export Post Failed");
		    integerNegativeErrorAlert
			    .setContentText(integerNegativeError.getMessage() + "\nClick OK to continue to next post.");
		    integerNegativeErrorAlert.showAndWait();
		} catch (InvalidContentException contentFormatError) {
		    Alert integerNegativeErrorAlert = new Alert(AlertType.ERROR);
		    integerNegativeErrorAlert.setHeaderText("Add Post Failed");
		    integerNegativeErrorAlert
			    .setContentText(contentFormatError.getMessage() + "\nClick OK to continue to next post.");
		    integerNegativeErrorAlert.showAndWait();
		} catch (ParseException e) {
		    Alert parseErrorAlert = new Alert(AlertType.ERROR);
		    parseErrorAlert.setHeaderText("Add Post Failed");
		    parseErrorAlert.setContentText(
			    "Invalid date-time value or/and format. The date-time must be in the format of DD/MM/YYYY HH:MM."
				    + "\nClick OK to continue to next post.");
		    parseErrorAlert.showAndWait();
		}

	    }

	    scanner.close(); /* Close scanner. */

	    inputStream.close(); /* Close posts.csv. */

	    Alert importPostStatus = new Alert(AlertType.INFORMATION);
	    importPostStatus.setHeaderText("Bulk Import Post Status");
	    importPostStatus.setContentText(String.format("%d post(s) is successfully imported ", postImportSuccess));
	    importPostStatus.showAndWait();

	} catch (NullPointerException fileIsNullError) {
	    Alert fileIsNullErrorAlert = new Alert(AlertType.ERROR);
	    fileIsNullErrorAlert.setHeaderText("Import Post Failed");
	    fileIsNullErrorAlert.setContentText("File is null. Please select a .csv file!");
	    fileIsNullErrorAlert.show();
	} catch (FileNotFoundException fileNotFoundError) {
	    Alert fileNotFoundErrorAlert = new Alert(AlertType.ERROR);
	    fileNotFoundErrorAlert.setHeaderText("Import Post Failed");
	    fileNotFoundErrorAlert.setContentText("File pathname did not exist! Please select a .csv file!");
	    fileNotFoundErrorAlert.show();
	} catch (IOException IOError) {
	    Alert IOErrorAlert = new Alert(AlertType.ERROR);
	    IOErrorAlert.setHeaderText("Import Post Failed");
	    IOErrorAlert.setContentText("Failed or interrupted I/O operations when reading file! Please try again!");
	    IOErrorAlert.show();
	}

    }

}
