/*

 * PostModel.java
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

import java.util.PriorityQueue;

import analytics.model.exceptions.*;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * 
 * The PostModel class serves as the Model for the Post in Data Analytics Hub
 * application.
 */
public class PostModel {

    private static PostModel postModel;

    private PostDatabaseHandler postDatabaseHandler;

    /**
     * Constructor PostModel to create PostDatabaseHandler object
     */
    private PostModel() {
	postDatabaseHandler = new PostDatabaseHandler();
    }

    /**
     * The method to create PostModel instance only if it did not exist
     * 
     */
    public static PostModel getInstance() {

	if (postModel != null) {
	    return postModel; // the reference is already referred to the object
	}

	// otherwise we create 1 object of the model and return it
	postModel = new PostModel();

	return postModel;
    }

    /**
     * The method to call createPost method in postDatabaseHandler to create a new
     * post in SQLite Database.
     * 
     * @param post The Post object to be create based on
     */
    public void createPost(Post post) {
	postDatabaseHandler.createPost(post);
    }

    /**
     * The method to call updatePost method in postDatabaseHandler to update an
     * existing post in SQLite Database.
     * 
     * @param outdatedUser The User object which its username will be used to search
     *                     for corresponding post
     * @param updatedUser  The User object which its username will be used to update
     *                     the post's author
     */
    public void updatePost(User outdatedUser, User updatedUser) {
	postDatabaseHandler.updatePost(outdatedUser, updatedUser);
    }

    /**
     * The method to call checkPostIDExist method in postDatabaseHandler to check if
     * post's ID already existed in SQLite Database
     * 
     * @param postID The post's ID integer input to check
     */
    public void checkPostIDExist(int postID) throws ExistedPostIDException {
	try {
	    postDatabaseHandler.checkPostIDExist(postID);
	} catch (ExistedPostIDException e) {
	    throw e;
	}
    }

    /**
     * The method to call retrievePost method in postDatabaseHandler to retrieve the
     * post from SQLite Database
     * 
     * @param postID The post's ID integer input to be retrieve
     */
    public Post retrievePost(int postID) {
	return postDatabaseHandler.retrievePost(postID);
    }

    /**
     * The method to call removePost method in postDatabaseHandler to remove the
     * post from SQLite Database
     * 
     * @param postID The post's ID integer input to be remove
     */
    public void removePost(int postID) {
	postDatabaseHandler.removePost(postID);
    }

    /**
     * The method to call retrieveTopNLikesPostAllUsers or
     * retrieveTopNLikesPostSingleUser method in postDatabaseHandler to retrieve the
     * post from SQLite Database
     * 
     * @param author The author which the post to be retrieve from
     * @return the list of posts
     */
    public PriorityQueue<Post> retrieveTopNLikesPost(String author) {
	if (author.equals("All Users")) {
	    return postDatabaseHandler.retrieveTopNLikesPostAllUsers();
	}
	return postDatabaseHandler.retrieveTopNLikesPostSingleUser(author);
    }

    /**
     * The method to call retrievePostSharePieChart method in postDatabaseHandler to
     * retrieve the post share distribution data from SQLite Database
     * 
     * @return observableArrayList containing the pie chart's data
     */
    public ObservableList<PieChart.Data> retrievePostSharePieChart() {
	return postDatabaseHandler.retrievePostSharePieChart();

    }

}