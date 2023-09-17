package analytics.model;

public class User {
    // Declaration of User's Instance Variables (private)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int vip;

    /**
     * Constructor User to initialize instance variables in the object
     * 
     * @param username  The user's username
     * @param password  The user's password
     * @param firstName The user's firstName
     * @param lastName  The user's lastName
     * @param vip       The user's vip status
     */
    public User(String username, String password, String firstName, String lastName, int vip) {
	this.username = username;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.vip = vip;
    }

    /**
     * The getter method to access user's username
     */
    public String getUsername() {
	return username;
    }

    /**
     * The setter method to set user's username
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * The getter method to access user's password
     */
    public String getPassword() {
	return password;
    }

    /**
     * The setter method to set user's password
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * The getter method to access user's firstName
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * The setter method to set user's firstName
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * The getter method to access user's lastName
     */
    public String getLastName() {
	return lastName;
    }

    /**
     * The setter method to set user's lastName
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }
    
    /**
     * The getter method to access user's vip status
     */
    public int getVip() {
	return vip;
    }

    /**
     * The setter method to set user's vip status
     */
    public void setVip(int vip) {
	this.vip = vip;
    }
}
