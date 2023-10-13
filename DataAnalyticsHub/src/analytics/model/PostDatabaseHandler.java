/*

 * PostDatabaseHandler.java
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
import java.util.PriorityQueue;

import analytics.model.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * 
 * The PostDatabaseHandler class provides CRUD methods for post-related SQL
 * query to access SQLite Database in Data Analytics Hub application.
 */
public class PostDatabaseHandler {

    /**
     * The method to create a new post record in SQLite Database
     * 
     * @param newPost The post object to be create
     */
    public void createPost(Post newPost) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format(
		    "INSERT INTO Posts (ID,CONTENT,AUTHOR,LIKES,SHARES,DATETIME) VALUES ('%d','%s', '%s', '%d', '%d', '%s')",
		    newPost.getId(), newPost.getContent(), newPost.getAuthor(), newPost.getLikes(), newPost.getShares(),
		    newPost.getDateTime());

	    int result = statement.executeUpdate(query);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * The method to check if a post's ID already exists in SQLite Database
     * 
     * @param postID The post's ID integers to be search
     */
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

    /**
     * The method to update a post's author in SQLite Database
     * 
     * @param outdatedUser The old User object which the post's author to be updated
     *                     from
     * @param updatedUser  The new User object which the post's author to be updated
     *                     to in SQLite Database
     */
    public void updatePost(User outdatedUser, User updatedUser) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {

	    String updateUserProfileQuery = String.format("UPDATE Posts SET AUTHOR = '%s' WHERE AUTHOR = '%s';",
		    updatedUser.getUsername(), outdatedUser.getUsername());

	    int result = statement.executeUpdate(updateUserProfileQuery);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * The method to remove Post record from the SQLite Database
     * 
     * @param postID The post ID to be remove
     * 
     */
    public void removePost(int postID) {
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("DELETE FROM Posts WHERE ID = '%d'", postID);

	    int result = statement.executeUpdate(query);

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * The method to retrieve Top N Post record from all users from the SQLite
     * Database
     * 
     * @return topNLikesPostAllUsers The priority queue containing Top N Post record
     *         from all users
     * 
     */
    public PriorityQueue<Post> retrieveTopNLikesPostAllUsers() {
	PriorityQueue<Post> topNLikesPostAllUsers = new PriorityQueue<Post>(new PostComparator());

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = "SELECT * FROM Posts";

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    topNLikesPostAllUsers.add(new Post(resultSet.getInt("ID"), resultSet.getString("CONTENT"),
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"), resultSet.getInt("SHARES"),
			    resultSet.getString("DATETIME")));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return topNLikesPostAllUsers;
    }

    /**
     * The method to retrieve Top N Post record from the specified author from the
     * SQLite Database
     * 
     * @param author The post's author to be retrieve from
     * @return topNLikesPostAllUsers The priority queue containing Top N Post record
     *         from the specified author
     * 
     */
    public PriorityQueue<Post> retrieveTopNLikesPostSingleUser(String author) {
	PriorityQueue<Post> topNLikesPostSingleUser = new PriorityQueue<Post>(new PostComparator());

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT * FROM Posts WHERE AUTHOR = '%s'", author);

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    topNLikesPostSingleUser.add(new Post(resultSet.getInt("ID"), resultSet.getString("CONTENT"),
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"), resultSet.getInt("SHARES"),
			    resultSet.getString("DATETIME")));
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return topNLikesPostSingleUser;
    }

    /**
     * The method to return Post object
     * 
     * @param postID The ID of the post which to be return
     * @return the Post object
     */
    public Post retrievePost(int postID) {
	Post post = null;
	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String.format("SELECT  * FROM Posts WHERE ID = '%d'", postID);

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    post = new Post(resultSet.getInt("ID"), resultSet.getString("CONTENT"),
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"), resultSet.getInt("SHARES"),
			    resultSet.getString("DATETIME"));
		}
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return post;
    }

    /**
     * The method to retrieve posts' shares distribution data from the SQLite
     * Database for pie chart's data
     * 
     * @return observableArrayList containing the pie chart's data
     * 
     */
    public ObservableList<PieChart.Data> retrievePostSharePieChart() {

	int Post0_99 = 0;
	int Post100_999 = 0;
	int Post1000_and_above = 0;

	try (Connection con = DatabaseConnection.getConnection(); Statement statement = con.createStatement();) {
	    String query = String
		    .format("SELECT " + " SUM(CASE WHEN (SHARES >= 0 AND SHARES <= 99) then 1 else 0 end) AS Post0_99, "
			    + " SUM(CASE WHEN (SHARES >=100 AND SHARES <= 999) then 1 else 0 end) AS Post100_999, "
			    + " SUM(CASE WHEN SHARES >=1000 then 1 else 0 end) AS Post1000_and_above " + "FROM Posts");

	    try (ResultSet resultSet = statement.executeQuery(query)) {
		while (resultSet.next()) {
		    Post0_99 = resultSet.getInt("Post0_99");
		    Post100_999 = resultSet.getInt("Post100_999");
		    Post1000_and_above = resultSet.getInt("Post1000_and_above");
		}
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}

	return FXCollections.observableArrayList(new PieChart.Data("0 - 99", Post0_99),
		new PieChart.Data("100 - 999", Post100_999), new PieChart.Data("1000 and above", Post1000_and_above));
    }
}
