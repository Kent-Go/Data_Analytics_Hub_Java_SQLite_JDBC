package analytics.model;

import java.util.PriorityQueue;

import analytics.model.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class PostModel {

    private static PostModel postModel;

    private PostDatabaseHandler postDatabaseHandler;

    private PostModel() {
	postDatabaseHandler = new PostDatabaseHandler();
    }

    public static PostModel getInstance() {
	
	if (postModel != null) {
	    return postModel; // the reference is already referred to the object
	}
	
	// otherwise we create 1 object of the model and return it
	postModel = new PostModel();

	return postModel;
    }

    public void createPost(Post post) {
	postDatabaseHandler.createPost(post);
    }

    public void updatePost(User outdatedUser, User updatedUser) {
	postDatabaseHandler.updatePost(outdatedUser, updatedUser);
    }

    public void checkPostIDExist(int postID) throws ExistedPostIDException {
	try {
	    postDatabaseHandler.checkPostIDExist(postID);
	} catch (ExistedPostIDException e) {
	    throw e;
	}
    }

    public void removePost(int postID) {
	postDatabaseHandler.removePost(postID);
    }

    public PriorityQueue<Post> retrieveTopNLikesPost(String author) {
	if (author.equals("All Users")) {
	    return postDatabaseHandler.retrieveTopNLikesPostAllUsers();
	}
	return postDatabaseHandler.retrieveTopNLikesPostSingleUser(author);
    }

    public Post retrievePost(int postID) {
	return postDatabaseHandler.retrievePost(postID);
    }

    public ObservableList<PieChart.Data> retrievePostSharePieChart() {
	return postDatabaseHandler.retrievePostSharePieChart();

    }

}