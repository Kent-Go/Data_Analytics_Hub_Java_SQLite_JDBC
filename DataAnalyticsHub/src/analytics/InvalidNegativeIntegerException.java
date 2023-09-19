/*

 * InvalidNegativeIntegerException.java
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
 * The InvalidNegativeIntegerException class is a user-defined exception for
 * negative integer.
 */
public class InvalidNegativeIntegerException extends Exception {
    public InvalidNegativeIntegerException() {
	super("Invalid response. Integer cannot be negative. Please try again!");
    }
}
