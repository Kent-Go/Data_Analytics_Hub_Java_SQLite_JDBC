package analytics.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import analytics.UserVerificationFailException;
import analytics.UsernameExistedException;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:DataAnalyticsHub.db";

    protected final HashMap<String, User> usersDatabase;

    public Database() {
	createUsersAndPostsTable();
	this.usersDatabase = new HashMap<String, User>();
	retreieveAllUsers();
    }

    // Method to return connection object
    private static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(DB_URL);
    }

    // Method to create new table
    private void createUsersAndPostsTable() {
	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    statement.addBatch("CREATE TABLE IF NOT EXISTS " + "Users" + "(USERNAME VARCHAR(15) NOT NULL,"
		    + "PASSWORD TEXT NOT NULL," + "FIRSTNAME TEXT NOT NULL," + "LASTNAME TEXT NOT NULL,"
		    + "VIP INT NOT NULL," + "PRIMARY KEY (USERNAME));");
	    statement.addBatch("CREATE TABLE IF NOT EXISTS " + "Posts" + "(ID INT NOT NULL," + "CONTENT TEXT NOT NULL,"
		    + "AUTHOR VARCHAR(15) NOT NULL," + "LIKES INT NOT NULL," + "SHARES INT NOT NULL,"
		    + "DATETIME TEXT NOT NULL," + "PRIMARY KEY (ID),"
		    + "FOREIGN KEY (AUTHOR) REFERENCES Users(USERNAME)ON UPDATE CASCADE" + ");");
	    statement.executeBatch();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    public void retreieveAllUsers() {
	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    String query = "SELECT * FROM Users";

	    try (ResultSet resultSetAllUsers = statement.executeQuery(query)) {
		while (resultSetAllUsers.next()) {
		    usersDatabase.put(resultSetAllUsers.getString("USERNAME"),
			    new User(resultSetAllUsers.getString("USERNAME"), resultSetAllUsers.getString("PASSWORD"),
				    resultSetAllUsers.getString("FIRSTNAME"), resultSetAllUsers.getString("LASTNAME"),
				    resultSetAllUsers.getInt("VIP")));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }
    
    public User retrieveUser(String username) {
	return usersDatabase.get(username);
    }

    public void createUser(User newUser) {
	usersDatabase.put(newUser.getUsername(), newUser);

	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
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

    public void verifyUser(String username, String password) throws UserVerificationFailException {
	if (usersDatabase.get(username) instanceof User) {
	    if (usersDatabase.get(username).getPassword().equals(password)) {
		return;
	    }
	}
	throw new UserVerificationFailException();
    }

    public void checkUserExist(String username) throws UsernameExistedException {
	if (usersDatabase.get(username) instanceof User) {
	    if (usersDatabase.get(username).getUsername().equals(username)) {
		throw new UsernameExistedException();
	    }
	}
    }

    public void updateUser(User outdatedUser, User updatedUser) {
	usersDatabase.remove(outdatedUser.getUsername());
	usersDatabase.put(updatedUser.getUsername(), updatedUser);

	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format(
		    "UPDATE Users SET USERNAME = '%s', PASSWORD = '%s', FIRSTNAME = '%s', LASTNAME = '%s' WHERE USERNAME = '%s';",
		    updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getFirstName(),
		    updatedUser.getLastName(), outdatedUser.getUsername());

	    int result = statement.executeUpdate(query);

	    if (result == 1) {
		System.out.println("User profile created successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }
}
