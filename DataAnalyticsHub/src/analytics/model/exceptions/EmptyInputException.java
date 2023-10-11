package analytics.model.exceptions;

/**
 * 
 * The EmptyInputException class is a user-defined exception for empty string.
 */
public class EmptyInputException extends Exception {
    public EmptyInputException() {
	super("Input is empty. Please type your response.");
    }
}