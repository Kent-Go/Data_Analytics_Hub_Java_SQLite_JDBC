package analytics.model;

import java.util.HashMap;

public class UserDAOImpl implements DAO<User> {
    
    HashMap<String, User> usersMap;

    public UserDAOImpl() {
	usersMap = new HashMap<String, User>();
	
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

}
