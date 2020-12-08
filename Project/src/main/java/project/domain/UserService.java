package project.domain;

import java.util.List;
import project.dao.UserDao;
import project.dao.UserDatabaseDao;

/**
 * Provides methods for handling Users.
 */
public class UserService {
    
    private User loggedIn;
    private UserDao userDao;
    private final StoreableObservationService observationService;

    public UserService(StoreableObservationService observationService) {
        this.userDao = new UserDatabaseDao("jdbc:sqlite:user.db");
        this.observationService = observationService;
    }
    
    public void setDatabase(UserDao database) {
        this.userDao = database;
    }

    
    /**
     * Tries to login with given User details.
     * Sets User loggedIn if login succeeds.
     * @param username username
     * @param password password
     * @return true if user is logged in, otherwise false.
     */
    public boolean login(String username, String password) throws Exception {
        User user = userDao.findUserByName(username, "username");
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(password)) {
            loggedIn = user;
            observationService.setLoggedUser(loggedIn);
            return true;
        }
        
        return false;
    }
    
    public User getLoggedUser() {
        return loggedIn;
    }    
    
    public void logout() {
        loggedIn = null;  
        observationService.setLoggedUser(loggedIn);
    }
    
    /**
     * Tries to create a new User.
     * @param username username
     * @param name name
     * @param password password
     * @return true if user is created, otherwise false.
     */
    public boolean createUser(String username, String name, String password) throws Exception  {   
        User user = userDao.findUserByName(username, "username");
        if (user != null) {
            return false;
        }
        user = new User(username, name, password);
        try {
            userDao.addUser(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public void modifyUser(int id, String username, String name, String password) throws Exception {
        userDao.modifyUser(id, username, name, password);
    }
    
    public void removeUser(int id) throws Exception {
        userDao.remove(id);
    }
    
    public User getUserById(int id) throws Exception {
        return userDao.findUserById(id);
    }
    
    
    public User getUserByName(String name, String searchField) throws Exception {
        return userDao.findUserByName(name, searchField);
    }
    
    public List<User> getAllUsers() throws Exception {
        return userDao.getAllUsers();
    }
    
    
}
