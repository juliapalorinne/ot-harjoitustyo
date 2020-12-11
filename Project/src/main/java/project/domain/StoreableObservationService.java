package project.domain;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import project.dao.ObservationDao;
import project.dao.ObservationDatabaseDao;

/**
 * Provides methods for handling StorableObservations.
 */
public class StoreableObservationService {
    private ObservationDao observationDao;
    private User loggedIn;
    
    public StoreableObservationService() {
        observationDao = new ObservationDatabaseDao("jdbc:sqlite:observation.db");
    }
    
    public void setDatabase(ObservationDao database) {
        observationDao = database;
    }
    
    /**
     * Tries to create a new StoreableObservation with only User information.
     * @return true if new observation created, otherwise false.
     */
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
    
    /**
     * Tries to create a new StoreableObservation with all information.
     * @param species Species
     * @param individuals number of Individuals
     * @param place Place
     * @param date date
     * @param time time (hh:mm)
     * @param info additional info
     * @return true if new observation created, otherwise false.
     */
    public boolean createObservation(Species species, int individuals, Place place, LocalDate date, LocalTime time, String info, int privacy) {
        StoreableObservation observation = new StoreableObservation(species.getId(), individuals, place.getId(), date, time, info, privacy, loggedIn.getUsername());
        try {   
            observationDao.addObservation(observation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    /**
     * Lists all Observations saved by the logged User.
     * @throws Exception Getting observation list failed.
     * @return The list of all Observations by User. If User not found, returns an empty list.
     */
    public List<StoreableObservation> getAll() throws Exception {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return observationDao.getAllObservations()
            .stream()
            .filter(o-> o.getUserId().equals(loggedIn.getUsername()))
            .collect(Collectors.toList());
    }
    
    /**
     * Lists all Observations saved by the logged User with the searchTerm in the searchField.
     * @param searchTerm search term
     * @param searchField the field to search
     * @throws Exception Getting observation list failed.
     * @return The list of all Observations by User. If User not found, returns an empty list.
     */
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
    
    public boolean modifyObservation(int id, Species species, int individuals, Place place, String date, String time, String info, int privacy) throws Exception {
        try {   
            observationDao.modifyObservation(id, species.getId(), individuals, place.getId(), date, time, info, privacy, loggedIn.getUsername());
        } catch (Exception ex) {
            return false;
        }
        return true;
        
    }
    

    public void setLoggedUser(User user) {
        this.loggedIn = user;
    }
    
    public User getLoggedUser() {
        return this.loggedIn;
    }    
}
