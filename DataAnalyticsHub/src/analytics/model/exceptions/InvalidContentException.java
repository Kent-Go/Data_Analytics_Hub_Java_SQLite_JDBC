/*

 * InvalidContentException.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */

package analytics.model.exceptions;

/**
 * 
 * The InvalidContentException class is a user-defined exception for string
 * content with "," included.
 */
public class InvalidContentException extends Exception {
    public InvalidContentException() {
	super("Invalid content. Do not contain ',' in your content. Please try again!");
    }
}
