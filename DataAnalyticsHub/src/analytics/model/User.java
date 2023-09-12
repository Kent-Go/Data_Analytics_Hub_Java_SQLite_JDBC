package analytics.model;

public class User {
    // Declaration of User's Instance Variables (private)
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    /**
     * Constructor User to initialize instance variables in the object
     * 
     * @param username  The user's username
     * @param password  The user's password
     * @param firstName The user's firstName
     * @param lastName  The user's lastName
     */
    public User(String username, String password, String firstName, String lastName) {
	this.username = username;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
    }
}
