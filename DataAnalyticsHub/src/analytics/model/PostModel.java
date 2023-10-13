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
     * @param id       The post id input
     * @param content  The post content input
     * @param author   The post author input
     * @param likes    The post likes input
     * @param shares   The post shares input
     * @param dateTime The post dateTime input
     * @throws EmptyInputException
     * @throws ExistedPostIDException
     * @throws InvalidNegativeIntegerException
     */
    public void createPost(String id, String content, String author, String likes, String shares, String dateTime)
	    throws EmptyInputException, ExistedPostIDException, InvalidNegativeIntegerException,
	    InvalidContentException, ParseException {

	try {
	    int postId = validator.readInputPostID(id); // post ID
	    int postLikes = validator.readInputNonNegativeInt(likes, "Number of Like"); // post number of like
	    int postShares = validator.readInputNonNegativeInt(shares, "Number of Share"); // post number of share
	    String postContent = validator.readInputContent(content); // post content
	    String postAuthor = validator.checkInputEmpty(author, "Author"); // post author
	    String postDateTime = validator.readInputDateTime(dateTime); // post date-time of creation

	    Post post = new Post(postId, postContent, postAuthor, postLikes, postShares, postDateTime);

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
     * The method for Validator to call checkPostIDExist method in
     * postDatabaseHandler to check if post's ID already existed in SQLite Database
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
     * @throws EmptyInputException
     * @throws InvalidNegativeIntegerException
     */
    public Post retrievePost(String postID) throws EmptyInputException, InvalidNegativeIntegerException {
	try {
	    int intInput = validator.readInputNonNegativeInt(postID, "Post ID");

	    return postDatabaseHandler.retrievePost(intInput);
	} catch (NumberFormatException e) {
	    throw e;
	} catch (EmptyInputException e) {
	    throw e;
	} catch (InvalidNegativeIntegerException e) {
	    throw e;
	}
    }

    /**
     * The method to call removePost method in postDatabaseHandler to remove the
     * post from SQLite Database
     * 
     * @param postID The post's ID integer input to be remove
     * @throws EmptyInputException,   InvalidNegativeIntegerException
     * @throws ExistedPostIDException
     */
    public boolean removePost(String postID) throws EmptyInputException, InvalidNegativeIntegerException {
	int id = 0;
	try {

	    id = validator.readInputPostID(postID);

	} catch (NumberFormatException e) {
	    throw e;
	} catch (EmptyInputException e) {
	    throw e;
	} catch (InvalidNegativeIntegerException e) {
	    throw e;
	} catch (ExistedPostIDException e) {
	    id = Integer.parseInt(postID);
	    postDatabaseHandler.removePost(id);
	    return true;
	}
	return false;
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
	    int intNumberPost = validator.readInputPositiveInt(numPost, "Number of Post");

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