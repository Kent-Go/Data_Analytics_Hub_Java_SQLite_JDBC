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

import java.text.ParseException;
import java.util.PriorityQueue;

import analytics.model.exceptions.*;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * 
 * The PostModel class serves as the Model for the Post in Data Analytics Hub
 * application. It validates the inputs.
 */
public class PostModel {

    private static PostModel postModel;

    private PostDatabaseHandler postDatabaseHandler;

    private Validator validator;

    /**
     * Constructor PostModel to create PostDatabaseHandler object
     */
    private PostModel() {
	postDatabaseHandler = new PostDatabaseHandler();
	validator = new Validator();
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
     * @throws EmptyInputException
     * @throws ExistedPostIDException
     * @throws InvalidNegativeIntegerException
     */
    public void createPost(String id, String content, String author, String likes, String shares, String dateTime)
	    throws EmptyInputException, ExistedPostIDException, InvalidNegativeIntegerException,
	    InvalidContentException, ParseException {

	try {
	    int postId = validator.readInputPostID(id); // post ID
	    int postLikes = validator.readInputNonNegativeInt(likes); // post number of like
	    int postShares = validator.readInputNonNegativeInt(shares); // post number of share
	    String postContent = validator.readInputContent(content); // post content
	    String postDateTime = validator.readInputDateTime(dateTime); // post date-time of creation

	    Post post = new Post(postId, postContent, author, postLikes, postShares, postDateTime);

	    postDatabaseHandler.createPost(post);
	} catch (EmptyInputException e) {
	    throw e;
	} catch (ExistedPostIDException e) {
	    throw e;
	} catch (InvalidNegativeIntegerException e) {
	    throw e;
	} catch (InvalidContentException e) {
	    throw e;
	} catch (ParseException e) {
	    throw e;
	}
    }

    /**
     * The method to call readInputRetrievePostID method from validator to get the
     * Post object.
     * 
     * @param id The post ID to be use to retrieve the post
     * @return post The post object retrieve from id
     */
    public Post validateInputRetrievePostID(String id) throws EmptyInputException, InvalidNegativeIntegerException {
	Post post = null;
	try {
	    post = validator.readInputRetrievePostID(id);
	} catch (NumberFormatException e) {
	    throw e;
	} catch (EmptyInputException e) {
	    throw e;
	} catch (InvalidNegativeIntegerException e) {
	    throw e;
	}
	return post;
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
     * @throws EmptyInputException, InvalidNegativeIntegerException
     */
    public Post removePost(String postID) throws EmptyInputException, InvalidNegativeIntegerException {
	Post post;
	try {
	    post = validator.readInputRetrievePostID(postID);
	    postDatabaseHandler.removePost(post.getId());
	} catch (NumberFormatException e) {
	    throw e;
	} catch (EmptyInputException e) {
	    throw e;
	} catch (InvalidNegativeIntegerException e) {
	    throw e;
	}
	return post;

    }

    /**
     * The method to call retrieveTopNLikesPostAllUsers or
     * retrieveTopNLikesPostSingleUser method in postDatabaseHandler to retrieve the
     * post from SQLite Database
     * 
     * @param numPost The number of post to retrieve from
     * @param author  The author which the post to be retrieve from
     * @return the list of posts
     * @throws EmptyInputException, InvalidNonPositiveIntegerException
     */
    public PriorityQueue<Post> retrieveTopNLikesPost(String numPost, String author)
	    throws EmptyInputException, InvalidNonPositiveIntegerException {

	PriorityQueue<Post> topNLikesPostQueue = null;
	try {
	    int intNumberPost = validator.readInputPositiveInt(numPost);

	    if (author.equals("All Users")) {
		topNLikesPostQueue = postDatabaseHandler.retrieveTopNLikesPostAllUsers();
	    } else {
		topNLikesPostQueue = postDatabaseHandler.retrieveTopNLikesPostSingleUser(author);
	    }

	} catch (NumberFormatException e) {
	    throw e;
	} catch (EmptyInputException e) {
	    throw e;
	} catch (InvalidNonPositiveIntegerException e) {
	    throw e;
	}
	return topNLikesPostQueue;
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