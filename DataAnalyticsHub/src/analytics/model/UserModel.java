package analytics.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import analytics.model.exceptions.UserVerificationFailException;
import analytics.model.exceptions.UsernameExistedException;
import javafx.collections.ObservableList;

public class UserModel {

    private static UserModel userModel;

    private UserDatabaseHandler userDatabaseHandler;

    private UserModel() {
	userDatabaseHandler = new UserDatabaseHandler();
    }

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

    public void createUser(User newUser) {
	userDatabaseHandler.createUser(newUser);
    }
    
    public void updateUser(User outdatedUser, User updatedUser) {
	userDatabaseHandler.updateUser(outdatedUser, updatedUser);
    }

    public void checkUserExist(String username) throws UsernameExistedException {
	try {
	    userDatabaseHandler.checkUserExist(username);
	} catch (UsernameExistedException e) {
	    throw e;
	}
    }

    public ObservableList<String> retreieveAllUsersName() {
	return userDatabaseHandler.retreieveAllUsersName();
    }
    
    public void upgradeUserToVip(User loginUser) {
	userDatabaseHandler.upgradeUserToVip(loginUser);
    }
}
