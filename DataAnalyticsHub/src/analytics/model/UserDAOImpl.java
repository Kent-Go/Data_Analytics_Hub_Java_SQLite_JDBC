package analytics.model;

import java.util.HashMap;

public class UserDAOImpl implements DAO<User> {
    
    HashMap<String, User> usersMap;

    public UserDAOImpl() {
	 
	//FIXTHIS: retrieve all data from database 
    }

    @Override
    public HashMap<String, User> retrieveAll() {
	return usersMap;
    }

    @Override
    public User retrieve(String username) {
	return usersMap.get(username); // return null if not found
    }

    @Override
    public void create(User user) {
	usersMap.put(user.getUsername(), user);
    }

    @Override
    public void update(User user) {
	usersMap.put(user.getUsername(), user);
    }
    
    public static void main(String[] args) {
	Database db = new Database();
	db.createUser("SD2C45", "abc123", "Stephen", "Curry");
	db.createUser("AB1W35", "abc123", "Kevin", "Durant");
	db.createUser("WE9C21", "abc123", "Lebron", "James");
	db.createUser("ABCDE", "abc123", "Shaq", "ONeal");
	db.createUser("QWERTY", "abc123", "Klay", "Thompson");
	db.retreieveAllUsers();
	db.usersDatabase.forEach((username, userObj) -> System.out.printf("%s %s\n", userObj.getVip(), userObj.getFirstName()));
    }

}
