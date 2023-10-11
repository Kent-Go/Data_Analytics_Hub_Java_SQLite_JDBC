package analytics.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PriorityQueue;

import analytics.model.exceptions.*;

public class PostDatabaseHandler {

    public void createPost(Post newPost) {

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
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
    
    public void updatePost(User outdatedUser, User updatedUser) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {

	    String updateUserProfileQuery = String.format(
		    "UPDATE Posts SET AUTHOR = '%s' WHERE AUTHOR = '%s';",
		    updatedUser.getUsername(), outdatedUser.getUsername());

	    int result = statement.executeUpdate(updateUserProfileQuery);

	    if (result == 1) {
		System.out.println("Posts author edited successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public void checkPostIDExist(int postID) throws ExistedPostIDException {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT  * FROM Posts WHERE ID = '%d'", postID);

	    ResultSet resultSet = statement.executeQuery(query);

	    if (resultSet.next() == true) {
		throw new ExistedPostIDException(postID);
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public void removePost(int postID) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("DELETE FROM Posts WHERE ID = '%d'", postID);

	    int result = statement.executeUpdate(query);

	    if (result == 1) {
		System.out.println("Remove post successfully");
		System.out.println(result + " row(s) affected");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    public PriorityQueue<Post> retrieveTopNLikesPostAllUsers() {
	PriorityQueue<Post> topNLikesPostAllUsers = new PriorityQueue<Post>(new PostComparator());

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = "SELECT * FROM Posts";

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    topNLikesPostAllUsers.add(new Post(resultSet.getInt("ID"), resultSet.getString("CONTENT"),
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"),
			    resultSet.getInt("SHARES"), resultSet.getString("DATETIME")));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return topNLikesPostAllUsers;

    }
    
    public PriorityQueue<Post> retrieveTopNLikesPostSingleUser(String author) {
	PriorityQueue<Post> topNLikesPostSingleUser = new PriorityQueue<Post>(new PostComparator());

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT * FROM Posts WHERE AUTHOR = '%s'", author);

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    topNLikesPostSingleUser.add(new Post(resultSet.getInt("ID"), resultSet.getString("CONTENT"),
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"),
			    resultSet.getInt("SHARES"), resultSet.getString("DATETIME")));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return topNLikesPostSingleUser;

    }
    
    public Post retrievePost(int postID) {
	Post post = null;
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT  * FROM Posts WHERE ID = '%d'", postID);

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    post = new Post(resultSet.getInt("ID"), resultSet.getString("CONTENT"),
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"),
			    resultSet.getInt("SHARES"), resultSet.getString("DATETIME"));
		}
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	
	return post;
    }
}
