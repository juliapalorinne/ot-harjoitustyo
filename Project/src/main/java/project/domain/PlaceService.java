package project.domain;

import java.util.List;
import project.dao.PlaceDao;
import project.dao.PlaceDatabaseDao;

/**
 * Provides methods for handling Places.
 */
public class PlaceService {
    
    private PlaceDao placeDao;
    
    /**
     * Starts a PlaceService with given database address.
     */
    public PlaceService() {
        placeDao = new PlaceDatabaseDao("jdbc:sqlite:place.db");
    }
    
    
    /**
     * Sets the database for Places.
     * 
     * @param database database
     */
    public void setDatabase(PlaceDao database) {
        this.placeDao = database;
    }
    
    
    /**
     * Tries to create a new Place.
     * 
     * @param country the country where the place is
     * @param city the city where the place is
     * @param spot the exact spot
     * @param type the type of the place
     * 
     * @return true if a new place is created, otherwise false.
     * 
     * @throws Exception Creating Place failed.
     */
    public boolean createPlace(String country, String city, String spot, String type) {
        try {   
            placeDao.addPlace(new Place(country, city, spot, type));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Tries to modify the Place with given id.
     * 
     * @param id id 
     * @param country the country where the place is
     * @param city the city where the place is
     * @param spot the exact spot
     * @param type the type of the place
     * 
     * @return true if a new place is created, otherwise false.
     * 
     * @throws Exception Modifying Place failed.
     */
    public boolean modifyPlace(int id, String country, String city, String spot, String type) throws Exception {
        try {   
            placeDao.modifyPlace(id, country, city, spot, type);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Tries to delete the Place with given id.
     * 
     * @param id id
     * 
     * @throws Exception Removing Place failed.
     */
    public void removePlace(int id) throws Exception {
        placeDao.remove(id);
    }
    
    
    /**
     * Tries to find a Place by id.
     * 
     * @param id id
     * 
     * @return the Place
     * 
     * @throws Exception Searching Place failed.
     */
    public Place getPlaceById(int id) throws Exception {
        return placeDao.findPlaceById(id);
    }
    
    
    /**
     * Tries to find a Place by name.
     * 
     * @param name name
     * @param searchField the field to search
     * 
     * @return the Place
     * 
     * @throws Exception Searching Place failed.
     */
    public Place getPlaceByName(String name, String searchField) throws Exception {
        return placeDao.findPlaceByName(name, searchField);
    }
    
    
    /**
     * Tries to get the list of all Places.
     * 
     * @return the list of Places
     * 
     * @throws Exception Listing all Places failed.
     */
    public List<Place> getAllPlaces() throws Exception {
        return placeDao.getAllPlaces();
    }
    
    
    /**
     * Finds list of Places by search term.
     * 
     * @param searchTerm the term to search
     * @param searchField the field to search
     * 
     * @return the list of Places
     * 
     * @throws Exception Listing the Places failed.
     */
    public List<Place> getPlaceBySearchTerm(String searchTerm, String searchField) throws Exception {
        return placeDao.searchPlaces(searchTerm, searchField);
    }
    
    
}
