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

    /**
     * Starts a UserService with given database address.
     * Sets StoreableObservationService.
     * 
     * @param observationService StoreableObservationService
     */
    public UserService(StoreableObservationService observationService) {
        this.userDao = new UserDatabaseDao("jdbc:sqlite:user.db");
        this.observationService = observationService;
    }
    
    
    /**
     * Sets the database for Users.
     * 
     * @param database database
     */
    public void setDatabase(UserDao database) {
        this.userDao = database;
    }

    
    /**
     * Tries to login with given User details.
     * Sets User loggedIn if login succeeds.
     * 
     * @param username username
     * @param password password
     * 
     * @return true if user is logged in, otherwise false.
     * 
     * @throws Exception Logging in failed.
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
    
    
    /**
     * Gets the logged User.
     * 
     * @return logged User.
     */
    public User getLoggedUser() {
        return loggedIn;
    }    
    
    
    /**
     * Sets the logged User as null.
     */
    public void logout() {
        loggedIn = null;
        observationService.setLoggedUser(loggedIn);
    }
    
    
    /**
     * Tries to create a new User.
     * 
     * @param username username
     * @param name name
     * @param password password
     * 
     * @return true if user is created, otherwise false.
     * 
     * @throws Exception Creating user failed.
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
    
    
    /**
     * Tries to modify the User.
     * 
     * @param id id
     * @param username username
     * @param name name
     * @param password password
     * 
     * @throws Exception Modifying User failed.
     */
    public void modifyUser(int id, String username, String name, String password) throws Exception {
        userDao.modifyUser(id, username, name, password);
    }
    
    
    /**
     * Tries to delete the User.
     * 
     * @param id id
     * 
     * @throws Exception Removing User failed.
     */
    public void removeUser(int id) throws Exception {
        userDao.remove(id);
    }
    
    
    /**
     * Tries to find the User by id.
     * 
     * @param id id
     * 
     * @return the User if exists, otherwise null.
     * 
     * @throws Exception Getting User failed.
     */
    public User getUserById(int id) throws Exception {
        return userDao.findUserById(id);
    }
    
    
    /**
     * Tries to find the User by name.
     * 
     * @param name name
     * @param searchField which name to search
     * 
     * @return the User if exists, otherwise null.
     * 
     * @throws Exception Modifying user failed.
     */
    public User getUserByName(String name, String searchField) throws Exception {
        return userDao.findUserByName(name, searchField);
    }
    
    
    /**
     * Tries to get the list of all Users.
     * 
     * @return list of the Users if any, otherwise null.
     * 
     * @throws Exception Modifying user failed.
     */
    public List<User> getAllUsers() throws Exception {
        return userDao.getAllUsers();
    }
    
    
}
