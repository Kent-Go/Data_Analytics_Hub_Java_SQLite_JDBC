/*

 * UserModel.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.model;

import analytics.model.exceptions.EmptyInputException;
import analytics.model.exceptions.InvalidPasswordLengthException;
import analytics.model.exceptions.UserVerificationFailException;
import analytics.model.exceptions.UsernameExistedException;
import javafx.collections.ObservableList;

/**
 * 
 * The UserModel class serves as the Model for the User in Data Analytics Hub
 * application. It validates the inputs.
 */
public class UserModel {

    private static UserModel userModel;

    private UserDatabaseHandler userDatabaseHandler;

    private Validator validator;

    /**
     * Constructor UserModel to create UserDatabaseHandler and Validator object
     */
    private UserModel() {
	userDatabaseHandler = new UserDatabaseHandler();
	validator = new Validator();
    }

    /**
     * The method to create UserModel instance only if it did not exist
     * 
     */
    public static UserModel getInstance() {

	if (userModel != null) {
	    return userModel; // the reference is already referred to the object
	}

	// otherwise we create 1 object of the model and return it
	userModel = new UserModel();

	return userModel;
    }

    /**
     * The method to call verifyUser method in userDatabaseHandler to create a new
     * user in SQLite Database. It first validates username and passwordbefore
     * calling verifyUser method.
     * 
     * @param username The username input
     * @param password The password input
     * @throws EmptyInputException
     * @throws UsernameExistedException
     * @throws InvalidPasswordLengthException
     */
    public User verifyUser(String username, String password) throws UserVerificationFailException, EmptyInputException {
	try {
	    String validatedUsername = validator.checkInputEmpty(username);
	    String validatedPassword = validator.checkInputEmpty(password);
	    return userDatabaseHandler.verifyUser(validatedUsername, validatedPassword);
	} catch (EmptyInputException e) {
	    throw e;
	} catch (UserVerificationFailException e) {
	    throw e;
	}
    }

    /**
     * The method to call createUser method in userDatabaseHandler to create a new
     * user in SQLite Database. It validates username, password, first name and last
     * input inputs before calling createUser method.
     * 
     * @param username  The username input
     * @param password  The password input
     * @param firstName The firstName input
     * @param lastName  The lastName input
     * @throws EmptyInputException
     * @throws UsernameExistedException
     * @throws InvalidPasswordLengthException
     */
    public void createUser(String username, String password, String firstName, String lastName)
	    throws EmptyInputException, UsernameExistedException, InvalidPasswordLengthException {

	try {
	    String validatedUsername = validator.checkInputEmpty(username);
	    String validatedPassword = validator.checkInputEmpty(password);
	    String validatedFirstName = validator.checkInputEmpty(firstName);
	    String validatedLastName = validator.checkInputEmpty(lastName);

	    checkUserExist(validatedUsername); /* checks if username already exists in SQLite Database */

	    validator.checkPasswordLength(password); /* checks password length */

	    userDatabaseHandler.createUser(new User(validatedUsername, validatedPassword, validatedFirstName,
		    validatedLastName, 0)); /* create new user profile record in SQLite Database */
	} catch (EmptyInputException e) {
	    throw e;
	} catch (UsernameExistedException e) {
	    throw e;
	} catch (InvalidPasswordLengthException e) {
	    throw e;
	}

    }

    /**
     * The method to call updateUser method in userDatabaseHandler to update an
     * existing user in SQLite Database. It validates username, password, first name
     * and last input inputs.
     * 
     * @param outdatedUser The User object which its username will be used to search
     *                     for corresponding record
     * @param username     The username input
     * @param password     The password input
     * @param firstName    The firstName input
     * @param lastName     The lastName input
     * @throws EmptyInputException
     * @throws EmptyInputException
     * @throws UsernameExistedException
     * @throws InvalidPasswordLengthException
     */
    public void updateUser(User outdatedUser, String username, String password, String firstName, String lastName)
	    throws EmptyInputException, UsernameExistedException, InvalidPasswordLengthException {

	try {
	    String validatedUsername = validator.checkInputEmpty(username);
	    String validatedPassword = validator.checkInputEmpty(password);
	    String validatedFirstName = validator.checkInputEmpty(firstName);
	    String validatedLastName = validator.checkInputEmpty(lastName);

	    if (!validatedUsername.equals(outdatedUser.getUsername())) { /* check if username exists in database */
		checkUserExist(validatedUsername);
	    }

	    validator.checkPasswordLength(validatedPassword); /* check password input length */

	    User updatedUser = new User(validatedUsername, validatedPassword, validatedFirstName, validatedLastName,
		    outdatedUser.getVip());

	    userDatabaseHandler.updateUser(outdatedUser, updatedUser); /* Update user profile in SQLite Database */

	} catch (EmptyInputException e) {
	    throw e;
	} catch (UsernameExistedException e) {
	    throw e;
	} catch (InvalidPasswordLengthException e) {
	    throw e;
	}

    }

    /**
     * The method to call checkUserExist method in userDatabaseHandler to check if
     * username already existed in SQLite Database
     * 
     * @param username The username string input to check
     */
    public void checkUserExist(String username) throws UsernameExistedException {
	try {
	    userDatabaseHandler.checkUserExist(username);
	} catch (UsernameExistedException e) {
	    throw e;
	}
    }

    /**
     * The method to call retreieveAllUsersName method in userDatabaseHandler to
     * retrieve all user's username from SQLite Database
     * 
     * @return the list of all usernames
     * 
     */
    public ObservableList<String> retreieveAllUsersName() {
	return userDatabaseHandler.retreieveAllUsersName();
    }

    /**
     * The method to call upgradeUserToVip method in userDatabaseHandler to upgrade
     * user vip status in SQLite Database.
     * 
     * @param loginUser The User object which its vip status to be upgrade
     */
    public void upgradeUserToVip(User loginUser) {
	userDatabaseHandler.upgradeUserToVip(loginUser);
    }
}
