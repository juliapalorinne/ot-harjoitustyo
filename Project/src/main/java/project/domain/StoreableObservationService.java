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
    
    /**
     * Starts a StoreableObservationService with given database address.
     */
    public StoreableObservationService() {
        observationDao = new ObservationDatabaseDao("jdbc:sqlite:observation.db");
    }
    
    
    /**
     * Sets the database for StoreableObservations.
     * 
     * @param database database
     */
    public void setDatabase(ObservationDao database) {
        observationDao = database;
    }
    
    
    /**
     * Tries to create a new StoreableObservation with only User information.
     * 
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
     * 
     * @param species Species
     * @param individuals number of Individuals
     * @param place Place
     * @param date date
     * @param time time (hh:mm)
     * @param info additional info
     * @param privacy privacy
     * 
     * @return true if new Observation created, otherwise false.
     */
    public boolean createObservation(Species species, int individuals, Place place, LocalDate date, LocalTime time, String info, int privacy) {
        StoreableObservation observation = new StoreableObservation(species.getId(), individuals, place.getId(), date, time, info, privacy, loggedIn.getUsername(), java.time.LocalDateTime.now());
        try {   
            observationDao.addObservation(observation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Lists all Observations saved by the logged User.
     * 
     * @return The list of all Observations by User.
     * If User not found, returns an empty list.
     * 
     * @throws Exception Getting observation list failed.
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
     * Lists all Observations saved by any User.
     * 
     * @return The list of all Observations by all Users.
     * 
     * @throws Exception Getting observation list failed.
     */
    public List<StoreableObservation> getAllByAllUsers() throws Exception {
        return observationDao.getAllObservations();
    }
    
    
    /**
     * Lists all Observations saved by the logged User with the searchTerm in the searchField.
     * 
     * @param searchTerm search term
     * @param searchField the field to search
     * 
     * @return The list of all Observations by User.
     * If User not found, returns an empty list.
     * 
     * @throws Exception Getting observation list failed.
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
    
    
    /**
     * Tries to find one Observation by id.
     * 
     * @param id id
     * 
     * @return the Observation if found
     * 
     * @throws Exception Finding Observation failed.
     */
    public StoreableObservation findObservationById(int id) throws Exception {
        return observationDao.findObservationById(id);
    }
    
    
    /**
     * Tries to delete one Observation by id.
     * 
     * @param id id
     * 
     * @throws Exception Deleting Observation failed.
     */
    public void removeObservation(int id) throws Exception {
        observationDao.remove(id);
    }
    
    
    /**
     * Tries to modify the Observation by id.
     * 
     * @param id id
     * @param species the Species as text
     * @param individuals number of Individuals
     * @param place the Place as text
     * @param date date
     * @param time time (hh:mm)
     * @param info additional info
     * @param privacy privacy
     * 
     * @return true if new Observation created, otherwise false.
     * 
     * @throws Exception Modifying Observation failed.
     */
    public boolean modifyObservation(int id, Species species, int individuals, Place place, String date, String time, String info, int privacy) throws Exception {
        try {   
            observationDao.modifyObservation(id, species.getId(), individuals, place.getId(), date, time, info, privacy, loggedIn.getUsername(), java.time.LocalDateTime.now().toString());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Tries to set User.
     * 
     * @param user user
     * 
     * @throws Exception Setting logged User failed.
     */
    public void setLoggedUser(User user) {
        this.loggedIn = user;
    }
    
    
    /**
     * Gets logged User.
     * 
     * @return the logged User
     * 
     * @throws Exception Getting logged User failed.
     */
    public User getLoggedUser() {
        return this.loggedIn;
    }    
}
