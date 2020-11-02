
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.UserDao;

public class UserService {
    private ObservationDao obsDao;
    private UserDao userDao;
    private User loggedIn;
    
    public UserService(ObservationDao obsDao, UserDao userDao) {
        this.userDao = userDao;
        this.obsDao = obsDao;
    }

    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        loggedIn = user;
        return true;
    }
    
    
    public User getLoggedUser() {
        return loggedIn;
    }    
    
    public void logout() {
        loggedIn = null;  
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
