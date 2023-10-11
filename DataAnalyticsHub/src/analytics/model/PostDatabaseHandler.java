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

	    String updateUserProfileQuery = String.format("UPDATE Posts SET AUTHOR = '%s' WHERE AUTHOR = '%s';",
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
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"), resultSet.getInt("SHARES"),
			    resultSet.getString("DATETIME")));
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
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"), resultSet.getInt("SHARES"),
			    resultSet.getString("DATETIME")));
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
			    resultSet.getString("AUTHOR"), resultSet.getInt("LIKES"), resultSet.getInt("SHARES"),
			    resultSet.getString("DATETIME"));
		}
	    }

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return post;
    }

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
