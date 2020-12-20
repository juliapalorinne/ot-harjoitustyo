package project.dao;

import java.util.List;
import project.domain.Place;

/**
 * PlaceDao Interface.
 */
public interface PlaceDao {
    
    
    /**
     * Adds new place.
     * 
     * @param place new Place
     * 
     * @throws Exception Adding to database failed.
     */
    public void addPlace(Place place) throws Exception;
    
    
    /**
     * Finds Place by id and modifies it.
     * 
     * @param id the Place id
     * @param country the country of the Place
     * @param city the city of the Place
     * @param spot the exact spot
     * @param type the type of the Place
     * 
     * @throws Exception Accessing database failed.
     */
    public void modifyPlace(int id, String country, String city, String spot, String type) throws Exception;
    
    
    /**
     * Deletes a Place from the database.
     * 
     * @param id the id of the Place
     * 
     * @throws Exception Deleting from database failed.
     */
    public void remove(int id) throws Exception;
    
    
     /**
     * Searched database to find a Place by id.
     * 
     * @param id the Place id
     * 
     * @return the Place
     * 
     * @throws Exception Searching database failed.
     */
    public Place findPlaceById(int id) throws Exception;
    
    
    /**
     * Searched database to find a Place by name.
     * 
     * @param name the name of the wanted Place
     * @param searchField the field to search
     * 
     * @return the Place
     * 
     * @throws Exception Searching database failed.
     */
    public Place findPlaceByName(String name, String searchField) throws Exception;

    
    /**
     * Returns all Places in the database.
     * 
     * @return the list of Places
     * 
     * @throws Exception Searching database failed.
     */
    public List<Place> getAllPlaces() throws Exception;

    
    /**
     * Returns Places with searchTerm in searchField.
     * 
     * @param searchTerm searched term
     * @param searchField the field to search
     * 
     * @return the list of Places
     * 
     * @throws Exception Searching database failed.
     */
    public List<Place> searchPlaces(String searchTerm, String searchField) throws Exception;
}
