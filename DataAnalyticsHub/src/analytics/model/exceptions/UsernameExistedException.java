package analytics.model.exceptions;

public class UsernameExistedException extends Exception {
    public UsernameExistedException() {
	super("Username is already registered. Please try again.");
    }
}
