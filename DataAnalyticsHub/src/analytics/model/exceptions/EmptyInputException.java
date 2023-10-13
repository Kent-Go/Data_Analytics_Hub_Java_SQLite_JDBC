/*
 * EmptyInputException.java
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
 * The EmptyInputException class is a user-defined exception for empty string.
 */
public class EmptyInputException extends Exception {
    public EmptyInputException(String inputField) {
	super(String.format("%s Input is empty. Please type your response.", inputField));
    }
}