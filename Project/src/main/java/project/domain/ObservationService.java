
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.ObservationDatabaseDao;
import project.dao.UserDao;

public class ObservationService {
    private ObservationDao observationDao;
    private User loggedIn;
    
    public ObservationService() {
        observationDao = new ObservationDatabaseDao("jdbc:sqlite:observation.db");
    }
    
    public void setDatabase(String databaseAddress) {
        observationDao = new ObservationDatabaseDao(databaseAddress);
    }
    
    public boolean createObservation() {
        Observation observation = new Observation();
        observation.setUser(loggedIn.getUsername());
        try {   
            observationDao.addObservation(observation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean createObservation(int species, int individuals, int place, LocalDate date, LocalTime time, String info) {
        Observation observation = new Observation(species, individuals, place, date, time, info, loggedIn.getUsername());
        try {   
            observationDao.addObservation(observation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Observation> getAll() throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
          
        return observationDao.getAllObservations()
            .stream()
            .filter(o-> o.getUserId().equals(loggedIn.getUsername()))
            .collect(Collectors.toList());
    }
    

    public void setLoggedUser(User user) {
        this.loggedIn = user;
    }
    
    public User getLoggedUser() {
        return this.loggedIn;
    }    
}
