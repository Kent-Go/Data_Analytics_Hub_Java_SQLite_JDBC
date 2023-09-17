package analytics;

public class UserVerificationFailException extends Exception {
    public UserVerificationFailException() {
	super("Username and/or password is incorrect. Please try again.");
    }
}
