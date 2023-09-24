package analytics.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.PriorityQueue;

import analytics.ExistedPostIDException;
import analytics.UserVerificationFailException;
import analytics.UsernameExistedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:DataAnalyticsHub.db";

    private HashMap<String, User> usersDatabase;

    private HashMap<Integer, Post> postsDatabase;

    public Database() {
	usersDatabase = new HashMap<String, User>();
	postsDatabase = new HashMap<Integer, Post>();

	createUsersAndPostsTable();
	retreieveAllUsers();
	retreieveAllPosts();
    }

    // Method to return connection object
    private static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(DB_URL);
    }

    // Method to create new table
    private void createUsersAndPostsTable() {
	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    statement.addBatch("PRAGMA foreign_keys = ON;");
	    statement.addBatch("CREATE TABLE IF NOT EXISTS " + "Users" + "(USERNAME VARCHAR(15) NOT NULL,"
		    + "PASSWORD TEXT NOT NULL," + "FIRSTNAME TEXT NOT NULL," + "LASTNAME TEXT NOT NULL,"
		    + "VIP INT NOT NULL," + "PRIMARY KEY (USERNAME));");
	    statement.addBatch("CREATE TABLE IF NOT EXISTS " + "Posts" + "(ID INT NOT NULL," + "CONTENT TEXT NOT NULL,"
		    + "AUTHOR VARCHAR(15) NOT NULL," + "LIKES INT NOT NULL," + "SHARES INT NOT NULL,"
		    + "DATETIME TEXT NOT NULL," + "PRIMARY KEY (ID),"
		    + "FOREIGN KEY (AUTHOR) REFERENCES Users(USERNAME) ON UPDATE CASCADE " + ");");
	    statement.executeBatch();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    public void retreieveAllUsers() {
	usersDatabase.clear();
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
    
    public ObservableList<String> retreieveAllUsersName() {
	ObservableList<String> authorList = FXCollections.observableArrayList();

	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    String query = "SELECT DISTINCT USERNAME FROM Users";

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

	    String updateUserProfileQuery = String.format(
		    "UPDATE Users SET USERNAME = '%s', PASSWORD = '%s', FIRSTNAME = '%s', LASTNAME = '%s' WHERE USERNAME = '%s';",
		    updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getFirstName(),
		    updatedUser.getLastName(), outdatedUser.getUsername());

	    String updateUserPostQuery = String.format("UPDATE Posts SET AUTHOR = '%s' WHERE AUTHOR = '%s';",
		    updatedUser.getUsername(), outdatedUser.getUsername());

	    int result1 = statement.executeUpdate(updateUserProfileQuery);
	    int result2 = statement.executeUpdate(updateUserPostQuery);

	    if (result2 == 1) {
		System.out.println("User profile edited successfully");
		System.out.println(result2 + " row(s) affected");
		System.out.println(result2);
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	retreieveAllPosts();
    }

    public void retreieveAllPosts() {
	postsDatabase.clear();
	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    String query = "SELECT * FROM Posts";

	    try (ResultSet resultSetAllPosts = statement.executeQuery(query)) {
		while (resultSetAllPosts.next()) {
		    postsDatabase.put(resultSetAllPosts.getInt("ID"),
			    new Post(resultSetAllPosts.getInt("ID"), resultSetAllPosts.getString("CONTENT"),
				    resultSetAllPosts.getString("AUTHOR"), resultSetAllPosts.getInt("LIKES"),
				    resultSetAllPosts.getInt("SHARES"), resultSetAllPosts.getString("DATETIME")));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public Post retrievePost(int id) {
	return postsDatabase.get(id);
    }

    public void createPost(Post newPost) {
	postsDatabase.put(newPost.getId(), newPost);

	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format(
		    "INSERT INTO Posts (ID,CONTENT,AUTHOR,LIKES,SHARES,DATETIME) VALUES ('%d','%s', '%s', '%d', '%d', '%s')",
		    newPost.getId(), newPost.getContent(), newPost.getAuthor(), newPost.getLikes(), newPost.getShares(),
		    newPost.getDateTime());

	    int result = statement.executeUpdate(query);

	    if (result == 1) {
		System.out.println("New post created successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    public void removePost(int id) {
	postsDatabase.remove(id);

	try (Connection con = getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("DELETE FROM Posts WHERE ID = '%d'", id);

	    int result = statement.executeUpdate(query);

	    if (result == 1) {
		System.out.println("Remove post successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

    }

    public PriorityQueue<Post> retrieveTopNLikesPost(String author) {
	PriorityQueue<Post> topNLikesPost = new PriorityQueue<Post>(new PostComparator());

	if (!author.equals("All Users")) {
	    postsDatabase.forEach((postId, postObject) -> {
		if (postObject.getAuthor().equals(author)) {
		    topNLikesPost.add(postObject);
		}
	    });
	} else {
	    postsDatabase.forEach((postId, postObject) ->  topNLikesPost.add(postObject));
	}
	
	return topNLikesPost;
    }

    /**
     * The method to check if postID is existed in database to throw user-defined
     * ExistedPostIDException
     * 
     * @param integer The postID integer to be validate
     */
    public void checkPostIDExist(int id) throws ExistedPostIDException {
	if (postsDatabase.get(id) instanceof Post) {
	    throw new ExistedPostIDException(id);
	}
    }

}
