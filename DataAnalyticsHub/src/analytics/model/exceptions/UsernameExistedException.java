/*
 * UsernameExistedException.java
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
 * The UsernameExistedException class is a user-defined exception for existed username.
 */
public class UsernameExistedException extends Exception {
    public UsernameExistedException() {
	super("Username is already registered. Please try again.");
    }
}
