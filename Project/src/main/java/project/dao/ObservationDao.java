
package project.dao;

import java.util.List;
import project.domain.StoreableObservation;

/**
 * ObservationDao Interface.
 */
public interface ObservationDao {
    
    
    /**
     * Adds new Observation.
     * 
     * @param observation new StoreableObservation
     * 
     * @throws Exception Adding to database failed.
     */
    public void addObservation(StoreableObservation observation) throws Exception;
     
    
    /**
     * Finds an Observation by id and modifies it.
     * 
     * @param id the Observation id
     * @param species the id of the Species of the Observation
     * @param individuals the number of individuals
     * @param place the id of the Place of the Observation
     * @param date the date of the Observation
     * @param time the time of the Observation (HH:mm)
     * @param info additional info
     * @param privacy 1 if observation is private, 0 if public
     * @param username username of the User
     * @param savingTime the time of the latest modification
     * 
     * @throws Exception Accessing database failed.
     */
    public void modifyObservation(int id, int species, int individuals, int place,
            String date, String time, String info, int privacy, String username, String savingTime) throws Exception;
    
    
    /**
     * Deletes an Observation from the database.
     * 
     * @param id the id of the Observation
     * 
     * @throws Exception Deleting from database failed.
     */
    public void remove(int id) throws Exception;
    
    
    /**
     * Returns an Observation if found by id from the database.
     * 
     * @param id the id of the wanted Observation
     * 
     * @return the Observation
     * 
     * @throws Exception Searching database failed.
     */
    public StoreableObservation findObservationById(int id) throws Exception;

    
    /**
     * Returns all Observations in the database.
     * 
     * @return the list of Observations
     * 
     * @throws Exception Searching database failed.
     */
    public List<StoreableObservation> getAllObservations() throws Exception;
    
    
    /**
     * Returns Observations with searchTerm in searchField.
     * 
     * @param searchTerm searched term
     * @param searchField the field to search
     * 
     * @return the list of Observations
     * 
     * @throws Exception Searching database failed.
     */
    public List<StoreableObservation> searchObservations(String searchTerm, String searchField) throws Exception;
    
}
