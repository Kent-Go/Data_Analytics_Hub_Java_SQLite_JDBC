/*

 * Post.java
 * 
 * Version: 1.0
 *
 * Date: 31/07/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */

package analytics.model;

/**
 * 
 * The Post class serves as a Post object with properties include id, content,
 * author, likes, shares and date-time. It provides getter methods to access the
 * Post's private instance variables.
 */
public class Post {
    // Declaration of Pots's Instance Variables (private)
    private int id;
    private String content;
    private String author;
    private int likes;
    private int shares;
    private String dateTime;

    /**
     * Constructor Post to initialize instance variables in the object
     * 
     * @param id       The post's ID
     * @param content  The post's content
     * @param author   The post's author
     * @param likes    The post's number of likes
     * @param shares   The post's number of shares
     * @param dateTime The post's date-time of creation
     */
    public Post(int id, String content, String author, int likes, int shares, String dateTime) {
	this.id = id;
	this.content = content;
	this.author = author;
	this.likes = likes;
	this.shares = shares;
	this.dateTime = dateTime;
    }

    /**
     * The method to represent the Post object in String format
     */
    public String toString() {
	return id + " | " + content + " | " + author + " | " + likes + " | " + shares + " | " + dateTime;
    }

    /**
     * The getter method to access Post's ID
     */
    public int getId() {
	return id;
    }

    /**
     * The getter method to access Post's content
     */
    public String getContent() {
	return content;
    }

    /**
     * The getter method to access Post's author
     */
    public String getAuthor() {
	return author;
    }

    /**
     * The getter method to access Post's number of likes
     */
    public int getLikes() {
	return likes;
    }

    /**
     * The getter method to access Post's number of shares
     */
    public int getShares() {
	return shares;
    }

    /**
     * The getter method to access Post's date-time of creation
     */
    public String getDateTime() {
	return dateTime;
    }
}
