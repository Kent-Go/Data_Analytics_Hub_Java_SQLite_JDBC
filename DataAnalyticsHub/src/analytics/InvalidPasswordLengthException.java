package analytics;

public class InvalidPasswordLengthException extends Exception {
    public InvalidPasswordLengthException() {
	super("Password length must be at least 8 characters. Please try again.");
    }
}
