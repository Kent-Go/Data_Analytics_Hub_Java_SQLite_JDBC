/*

 * UserDatabaseHandler.java
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import analytics.model.exceptions.UserVerificationFailException;
import analytics.model.exceptions.UsernameExistedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * The UserDatabaseHandler class serves as the user-related SQL query handler for the SQLite
 * Database in Data Analytics Hub application.
 */
public class UserDatabaseHandler {

    /**
     * The method to create a new user record in SQLite Database
     * 
     * @param newUser The user object to be create
     */
    public void createUser(User newUser) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format(
		    "INSERT INTO Users (USERNAME,PASSWORD,FIRSTNAME,LASTNAME, VIP) VALUES ('%s','%s', '%s', '%s', '%d')",
		    newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName(),
		    newUser.getVip());

	    int result = statement.executeUpdate(query);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    /**
     * The method to check if a username already exists in SQLite Database
     * 
     * @param username The username string to be search
     */
    public void checkUserExist(String username) throws UsernameExistedException {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT  * FROM Users WHERE USERNAME = '%s'", username);

	    ResultSet resultSet = statement.executeQuery(query);

	    if (resultSet.next() == true) {
		throw new UsernameExistedException();
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * The method to verify if a user exists in SQLite Database using username and
     * password
     * 
     * @param username The username string to be search
     * @param password The password string to be search
     * @return loginUser The User object of the verified username and password
     */
    public User verifyUser(String username, String password) throws UserVerificationFailException {
	User loginUser = null;
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT  * FROM Users WHERE USERNAME = '%s' AND PASSWORD = '%s'", username,
		    password);

	    ResultSet resultSet = statement.executeQuery(query);

	    if (resultSet.next() == false) {
		throw new UserVerificationFailException();
	    } else {
		loginUser = new User(resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"),
			resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getInt("VIP"));

	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return loginUser;
    }

    /**
     * The method to update a user profile in SQLite Database
     * 
     * @param outdatedUser The old User object to be update from
     * @param updatedUser  The new User object to be update into SQLite Database
     */
    public void updateUser(User outdatedUser, User updatedUser) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {

	    String updateUserProfileQuery = String.format(
		    "UPDATE Users SET USERNAME = '%s', PASSWORD = '%s', FIRSTNAME = '%s', LASTNAME = '%s' WHERE USERNAME = '%s';",
		    updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getFirstName(),
		    updatedUser.getLastName(), outdatedUser.getUsername());

	    int result = statement.executeUpdate(updateUserProfileQuery);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * The method to retrieve a list of usernames from SQLite Database
     * 
     * @return authorList The list of username in SQLite Database
     * 
     */
    public ObservableList<String> retreieveAllUsersName() {
	ObservableList<String> authorList = FXCollections.observableArrayList();

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = "SELECT USERNAME FROM Users";

	    try (ResultSet resultSetAllUsers = statement.executeQuery(query)) {
		while (resultSetAllUsers.next()) {
		    authorList.add(resultSetAllUsers.getString("USERNAME"));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return authorList;
    }

    /**
     * The method to upgrade the user's vip status in SQLite Database
     * 
     * @param user The User object which its vip status to be upgrade
     * 
     */
    public void upgradeUserToVip(User user) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {

	    String updateUserVipQuery = String.format("UPDATE Users SET VIP = '1' WHERE USERNAME = '%s';",
		    user.getUsername());

	    int result = statement.executeUpdate(updateUserVipQuery);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

}
