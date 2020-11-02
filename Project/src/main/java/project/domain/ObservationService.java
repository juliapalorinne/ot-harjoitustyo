
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.UserDao;

public class ObservationService {
    private ObservationDao obsDao;
    private UserDao userDao;
    private User loggedIn;
    
    public ObservationService(ObservationDao obsDao, UserDao userDao) {
        this.userDao = userDao;
        this.obsDao = obsDao;
    }
    
    
    public boolean createObservation(String species, String place, Date date, LocalTime time, String info) {
        Observation obs = new Observation(species, place, date, time, info, loggedIn);
        try {   
            obsDao.create(obs);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Observation> getAll() {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
          
        return obsDao.getAll()
            .stream()
            .filter(o-> o.getUser().equals(loggedIn))
            .collect(Collectors.toList());
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

}