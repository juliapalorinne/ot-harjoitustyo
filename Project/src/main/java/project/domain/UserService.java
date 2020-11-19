
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.UserDao;

public class UserService {
    private UserDao userDao;
    private User loggedIn;
    private ObservationService observationService;
    
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean login(String username, String password) {
        User user = userDao.findByUsername(username);
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
    
    
    public boolean createUser(String username, String name, String password)  {   
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username, name, password);
        try {
            userDao.create(user);
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
