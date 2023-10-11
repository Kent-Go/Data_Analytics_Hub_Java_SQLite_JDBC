package analytics.model;

import java.util.PriorityQueue;

import analytics.model.exceptions.*;

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

}
//
//SELECT
//SUM(CASE WHEN City = 'London' then 1 else 0 end) AS LondonCount,
//SUM(CASE WHEN City = 'Madrid' then 1 else 0 end) AS MadridCount,
//SUM(CASE WHEN City = 'México D.F.' then 1 else 0 end) AS MexicoCount
//FROM [Customers]
