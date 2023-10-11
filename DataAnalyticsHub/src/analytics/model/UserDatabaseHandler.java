package analytics.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import analytics.model.exceptions.UserVerificationFailException;
import analytics.model.exceptions.UsernameExistedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDatabaseHandler {

    public void createUser(User newUser) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format(
		    "INSERT INTO Users (USERNAME,PASSWORD,FIRSTNAME,LASTNAME, VIP) VALUES ('%s','%s', '%s', '%s', '%d')",
		    newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(), newUser.getLastName(),
		    newUser.getVip());

	    int result = statement.executeUpdate(query);

	    if (result == 1) {
		System.out.println("User profile created successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

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

    public User verifyUser(String username, String password) throws UserVerificationFailException {
	User loginUser = null;
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT  * FROM Users WHERE USERNAME = '%s' AND PASSWORD = '%s'", username,
		    password);

	    ResultSet resultSet = statement.executeQuery(query);

	    if (resultSet.next() == false) {
		throw new UserVerificationFailException();
	    }
	    else {
		loginUser = new User(resultSet.getString("USERNAME"), resultSet.getString("PASSWORD"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getInt("VIP"));

	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	
	return loginUser;
    }
    
    public void updateUser(User outdatedUser, User updatedUser) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {

	    String updateUserProfileQuery = String.format(
		    "UPDATE Users SET USERNAME = '%s', PASSWORD = '%s', FIRSTNAME = '%s', LASTNAME = '%s' WHERE USERNAME = '%s';",
		    updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getFirstName(),
		    updatedUser.getLastName(), outdatedUser.getUsername());

	    int result = statement.executeUpdate(updateUserProfileQuery);

	    if (result == 1) {
		System.out.println("User profile edited successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }
    
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
    
    public void upgradeUserToVip(User user) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {

	    String updateUserVipQuery = String.format("UPDATE Users SET VIP = '1' WHERE USERNAME = '%s';",
		    user.getUsername());

	    int result = statement.executeUpdate(updateUserVipQuery);

	    if (result == 1) {
		System.out.println("User Vip upgrade successfully");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

}
