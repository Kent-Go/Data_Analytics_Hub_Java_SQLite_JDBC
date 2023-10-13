/*
 * InvalidNonPositiveIntegerException.java
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
 * The InvalidNonPositiveIntegerException class is a user-defined exception for
 * non-positive integer.
 */
public class InvalidNonPositiveIntegerException extends Exception {
    public InvalidNonPositiveIntegerException(int integer, String inputField) {
	super(String.format("Invalid %s input. %d is negative or zero. Response must be positive. Please try again!", inputField, integer));
    }
}
