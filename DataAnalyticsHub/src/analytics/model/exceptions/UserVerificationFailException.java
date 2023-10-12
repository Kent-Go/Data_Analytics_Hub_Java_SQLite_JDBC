/*
 * UserVerificationFailException.java
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
 * The UserVerificationFailException class is a user-defined exception for failed username and password verification.
 */
public class UserVerificationFailException extends Exception {
    public UserVerificationFailException() {
	super("Username and/or password is incorrect. Please try again.");
    }
}
