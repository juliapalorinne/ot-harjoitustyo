
package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.ObservationDatabaseDao;

public class StoreableObservationService {
    private ObservationDao observationDao;
    private User loggedIn;
    
    public StoreableObservationService() {
        observationDao = new ObservationDatabaseDao("jdbc:sqlite:observation.db");
    }
    
    public void setDatabase(ObservationDao database) {
        observationDao = database;
    }
    
    public boolean createObservation() {
        StoreableObservation observation = new StoreableObservation();
        observation.setUser(loggedIn.getUsername());
        try {   
            observationDao.addObservation(observation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public boolean createObservation(Species species, int individuals, Place place, LocalDate date, LocalTime time, String info) {
        StoreableObservation observation = new StoreableObservation(species.getId(), individuals, place.getId(), date, time, info, loggedIn.getUsername());
        try {   
            observationDao.addObservation(observation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<StoreableObservation> getAll() throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
          
        return observationDao.getAllObservations()
            .stream()
            .filter(o-> o.getUserId().equals(loggedIn.getUsername()))
            .collect(Collectors.toList());
    }
    
    
    public List<StoreableObservation> getObservationsBySearchTerm(String searchTerm, String searchField) throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
          
        return observationDao.searchObservations(searchTerm, searchField)
            .stream()
            .filter(o-> o.getUserId().equals(loggedIn.getUsername()))
            .collect(Collectors.toList());
    }
    
    public StoreableObservation findObservationById(int id) throws Exception {
        return observationDao.findObservationById(id);
    }
    
    
    public void removeObservation(int id) throws Exception {
        observationDao.remove(id);
    }
    
    public void modifyObservation(int id, Species species, int individuals, Place place, String date, String time, String info) throws Exception {
        observationDao.modifyObservation(id, species.getId(), individuals, place.getId(), date, time, info, loggedIn.getUsername());
    }
    

    public void setLoggedUser(User user) {
        this.loggedIn = user;
    }
    
    public User getLoggedUser() {
        return this.loggedIn;
    }    
}
