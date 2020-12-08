
package project.domain;

import project.dao.UserDao;
import project.dao.UserDatabaseDao;

public class UserService extends StoringService {
    
    private User loggedIn;
    private final StoreableObservationService observationService;

    /**
     *
     */
    protected UserDao userDao;
    
    public UserService(StoreableObservationService observationService) {
        this.userDao = new UserDatabaseDao("jdbc:sqlite:user.db");
        this.observationService = observationService;
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
    
    public void modify(int id, String username, String name, String password) throws Exception {
        userDao.modifyUser(id, username, name, password);
    }
}
