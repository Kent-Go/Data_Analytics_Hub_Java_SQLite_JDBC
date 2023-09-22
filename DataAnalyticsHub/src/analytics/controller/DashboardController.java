package analytics.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import analytics.EmptyInputException;
import analytics.InvalidPasswordLengthException;
import analytics.UsernameExistedException;
import analytics.ExistedPostIDException;
import analytics.InvalidNegativeIntegerException;

import analytics.model.Database;
import analytics.model.Post;
import analytics.model.User;
import analytics.view.EditProfileViewer;
import analytics.view.LoginViewer;
import analytics.InvalidContentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DashboardController {

    private Stage primaryStage;

    private User loginUser;

    private Database dataBase;

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

    public DashboardController() {
	dataBase = new Database();
    }
    
    public void setPrimaryStage(Stage primaryStage) {
   	this.primaryStage = primaryStage;
    }

    public void initaliseUser(User loginUser) {
	this.loginUser = loginUser;
	addPostAuthorLabelField.setText(this.loginUser.getUsername());
    }

    public void displayWelcomeMessage() {
	welcomeMessage.setText(String.format("Welcome, %s %s", loginUser.getFirstName(), loginUser.getLastName()));
    }

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

    @FXML
    public void logOutUserHandler(ActionEvent event) {
	Alert logOutSuccessAlert = new Alert(AlertType.INFORMATION);
	logOutSuccessAlert.setHeaderText("You are now logged out.");
	logOutSuccessAlert.setContentText("Click OK to proceed to login.");
	logOutSuccessAlert.showAndWait();
	redirectLoginPage(event);
    }

    public void redirectLoginPage(ActionEvent event) {
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

    @FXML
    public void addPostHandler(ActionEvent event) {

	try {
	    String id = addPostIDInputField.getText();
	    String postAuthor = addPostAuthorLabelField.getText(); // post author
	    String likes = addPostLikesInputField.getText();
	    String shares = addPostSharesInputField.getText();
	    String content = addPostContentInputField.getText();
	    String dateTime = addPostDateTimeInputField.getText();

	    int postId = readInputPostID(id); // post ID
	    int postLikes = readInputNonNegativeInt(likes); // post number of like
	    int postShares = readInputNonNegativeInt(shares); // post number of share
	    String postContent = readInputContent(content); // post content
	    String postDateTime = readInputDateTime(dateTime); // post date-time of creation

	    dataBase.createPost(new Post(postId, postContent, postAuthor, postLikes, postShares, postDateTime));

	    Alert addPostSuccess = new Alert(AlertType.INFORMATION);
	    addPostSuccess.setHeaderText("Add Post Success. Your new post is now saved.");
	    addPostSuccess.setContentText("Click OK to go back to dashboard.");
	    addPostSuccess.showAndWait();
	    
	    addPostIDInputField.setText("");
	    addPostAuthorLabelField.setText(""); // post author
	    addPostLikesInputField.setText("");
	    addPostSharesInputField.setText("");
	    addPostContentInputField.setText("");
	    addPostDateTimeInputField.setText("");
	} catch (ExistedPostIDException postIDExisted) {
	    Alert PostIDExistedAlert = new Alert(AlertType.ERROR);
	    PostIDExistedAlert.setHeaderText("Add Post Failed");
	    PostIDExistedAlert.setContentText(postIDExisted.getMessage());
	    PostIDExistedAlert.show();
	} catch (EmptyInputException inputEmptyError) {
	    Alert inputEmptyErrorAlert = new Alert(AlertType.ERROR);
	    inputEmptyErrorAlert.setHeaderText("Add Post Failed");
	    inputEmptyErrorAlert.setContentText(inputEmptyError.getMessage());
	    inputEmptyErrorAlert.show();
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
     * The method to read user's post ID input and call readInputNonNegativeInt to
     * validate the parsed integer format and check if post ID already existed in
     * database in order to return the non-exist post ID integer.
     * 
     * @param text The text to be print to prompt user's input
     * @return the the non-exist post ID integer input
     * @throws ExistedPostIDException
     * @throws EmptyInputException
     * @throws InvalidNegativeIntegerException
     */
    private int readInputPostID(String input)
	    throws ExistedPostIDException, EmptyInputException, InvalidNegativeIntegerException {
	int postID = 0;
	try {
	    input = input.trim();
	    postID = readInputNonNegativeInt(input);
	    dataBase.checkPostIDExist(postID);
	} catch (NumberFormatException e) {
	    throw new NumberFormatException();
	} catch (EmptyInputException e) {
	    throw new EmptyInputException();
	} catch (InvalidNegativeIntegerException e) {
	    throw new InvalidNegativeIntegerException();
	} catch (ExistedPostIDException postIDExisted) {
	    throw new ExistedPostIDException(postID);
	}

	return postID;
    }

    /**
     * The method to read post's content input and validate the content format in
     * order to return the valid content.
     * 
     * @param text        The text to be print to prompt user's input
     * @param inputStream The source which the input will be read from
     * @return the valid post's content input
     * @throws EmptyInputException
     * @throws InvalidContentException
     */
    private String readInputContent(String input) throws EmptyInputException, InvalidContentException {
	try {
	    input = input.trim();
	    checkInputEmpty(input);
	    checkContentFormat(input);
	} catch (EmptyInputException inputEmptyError) {
	    throw new EmptyInputException();
	} catch (InvalidContentException contentFormatError) {
	    throw new InvalidContentException();
	}
	return input;
    }

    /**
     * The method to read user's string input and validate the parsed integer format
     * in order to return the valid non-negative parsed integer.
     * 
     * @param text        The text to be print to prompt user's input
     * @param inputStream The source which the input will be read from
     * @return the valid parsed non-negative integer input
     * @throws EmptyInputException
     * @throws InvalidNegativeIntegerException
     */
    private int readInputNonNegativeInt(String input)
	    throws EmptyInputException, NumberFormatException, InvalidNegativeIntegerException {
	int intInput = 0;
	try {
	    checkInputEmpty(input);
	    intInput = Integer.parseInt(input);
	    checkNonNegativeIntegerFormat(intInput);
	} catch (EmptyInputException inputEmptyError) {
	    throw new EmptyInputException();
	} catch (NumberFormatException numberFormatError) {
	    throw new NumberFormatException();
	} catch (InvalidNegativeIntegerException integerNegativeError) {
	    throw new InvalidNegativeIntegerException();
	}

	return intInput;
    }

    /**
     * The method to read post's date-time input and validate the date-time format
     * in order to return the valid date-time.
     * 
     * @param text        The text to be print to prompt user's input
     * @param inputStream The source which the input will be read from
     * @return the valid post's date-time format
     * @throws EmptyInputException
     */
    private String readInputDateTime(String input) throws EmptyInputException, ParseException {
	// Note that SimpleDateFormat class and methods are adapted from:
	// https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	dateFormat.setLenient(false);
	try {
	    input = input.trim();
	    checkInputEmpty(input);
	    Date date = dateFormat.parse(input);
	    input = dateFormat.format(date);
	} catch (EmptyInputException inputEmptyError) {
	    throw new EmptyInputException();
	} catch (ParseException parseError) {
	    throw new ParseException(input, 0);
	}
	return input;
    }

    /**
     * The method to check if input is empty to throw user-defined
     * EmptyContentException
     * 
     * @param content The string to be validate
     */
    private void checkInputEmpty(String input) throws EmptyInputException {
	if (input.isEmpty()) {
	    throw new EmptyInputException();
	}
    }

    /**
     * The method to check if content contains "," to throw user-defined
     * InvalidContentException
     * 
     * @param content The string to be validate
     */
    private void checkContentFormat(String content) throws InvalidContentException {
	if (content.contains(",")) {
	    throw new InvalidContentException();
	}
    }

    /**
     * The method to check if integer is negative to throw user-defined
     * InvalidNegativeIntegerException
     * 
     * @param integer The integer to be validate
     */
    private void checkNonNegativeIntegerFormat(int integer) throws InvalidNegativeIntegerException {
	if (integer < 0) {
	    throw new InvalidNegativeIntegerException();
	}
    }
}
