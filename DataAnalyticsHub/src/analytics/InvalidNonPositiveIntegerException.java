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

package analytics;

/**
 * 
 * The InvalidNonPositiveIntegerException class is a user-defined exception for
 * non-positive integer.
 */
public class InvalidNonPositiveIntegerException extends Exception {
    public InvalidNonPositiveIntegerException() {
	super("Invalid response. Integer cannot be negative or zero. Please try again!");
    }
}
