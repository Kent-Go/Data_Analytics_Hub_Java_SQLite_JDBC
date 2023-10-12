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

import analytics.model.exceptions.UserVerificationFailException;
import analytics.model.exceptions.UsernameExistedException;
import javafx.collections.ObservableList;

/**
 * 
 * The UserModel class serves as the Model for the User in Data Analytics Hub
 * application.
 */
public class UserModel {

    private static UserModel userModel;

    private UserDatabaseHandler userDatabaseHandler;

    /**
     * Constructor UserModel to create UserDatabaseHandler object
     */
    private UserModel() {
	userDatabaseHandler = new UserDatabaseHandler();
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

    public User verifyUser(String username, String password) throws UserVerificationFailException {
	try {
	    return userDatabaseHandler.verifyUser(username, password);
	} catch (UserVerificationFailException e) {
	    throw e;
	}
    }

    /**
     * The method to call createUser method in userDatabaseHandler to create a new
     * user in SQLite Database.
     * 
     * @param newUser The User object to be create based on
     */
    public void createUser(User newUser) {
	userDatabaseHandler.createUser(newUser);
    }

    /**
     * The method to call updateUser method in userDatabaseHandler to update an
     * existing user in SQLite Database.
     * 
     * @param outdatedUser The User object which its username will be used to search
     *                     for corresponding record
     * @param updatedUser  The User object which its username will be used to update
     *                     the user profile's username
     */
    public void updateUser(User outdatedUser, User updatedUser) {
	userDatabaseHandler.updateUser(outdatedUser, updatedUser);
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
