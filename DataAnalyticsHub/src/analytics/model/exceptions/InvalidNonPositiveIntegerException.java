/*
 * InvalidNonPositiveIntegerException.java
 * 
 * Version: 1.0
 *
 * Date: 31/07/2023
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
    public InvalidNonPositiveIntegerException(int integer) {
	super(String.format("Invalid response. %d is negative or zero. Response must be positive. Please try again!", integer));
    }
}
