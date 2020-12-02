
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
    private User loggedIn;
    
    public ObservationService(ObservationDao obsDao) {
        this.obsDao = obsDao;
    }
    
    public boolean createObservation() {
        Observation obs = new Observation();
        obs.setUser(loggedIn);
        try {   
            obsDao.create(obs);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean createObservation(String species, int individuals, String place, LocalDate date, LocalTime time, String info) {
        Observation obs = new Observation(species, individuals, place, date, time, info, loggedIn);
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
    

    public void setLoggedUser(User user) {
        this.loggedIn = user;
    }
    
    public User getLoggedUser() {
        return this.loggedIn;
    }    
}
