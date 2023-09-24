/**
 * ExistedPostIDException.java
 * 
 * Version: 1.0
 *
 * Date: 31/07/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */

package analytics;

/**
 * 
 * The ExistedPostIDException class is a user-defined exception for invalid postID.
 */
public class ExistedPostIDException extends Exception {
    public ExistedPostIDException(int postID) {
	super("The post ID " + postID + " already existed in database. Please try again.");
    }
}
