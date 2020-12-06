
package project.domain;

import java.util.List;
import project.dao.UserDao;
import project.dao.UserDatabaseDao;

public class UserService {
    private UserDao userDao;
    private User loggedIn;
    private ObservationService observationService;
    
    public UserService(ObservationService observationService) {
        this.userDao = new UserDatabaseDao("jdbc:sqlite:user.db");
        this.observationService = observationService;
    }
    
    public void setDatabase(UserDao database) {
        userDao = database;
    }

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
    
    public void removeUser(int id) throws Exception {
        userDao.removeUser(id);
    }
    
    public void modifyUser(int id, String username, String name, String password) throws Exception {
        userDao.modifyUser(id, username, name, password);
    }
    
    public User getUserById(int id) throws Exception {
        User user = userDao.findUserById(id);
        return user;
    }
    
    public User getUserByName(String name, String searchField) throws Exception {
        User user = userDao.findUserByName(name, searchField);
        return user;
    }
    
    public List<User> getAllUsers() throws Exception {
        return userDao.getAllUsers();
    }
}
