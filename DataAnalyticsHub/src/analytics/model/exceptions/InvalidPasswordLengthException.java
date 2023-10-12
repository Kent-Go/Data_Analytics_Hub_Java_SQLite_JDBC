/*
 * InvalidPasswordLengthException.java
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
 * The InvalidPasswordLengthException class is a user-defined exception for incorrect password length.
 */
public class InvalidPasswordLengthException extends Exception {
    public InvalidPasswordLengthException() {
	super("Password length must be at least 6 characters. Please try again.");
    }
}
