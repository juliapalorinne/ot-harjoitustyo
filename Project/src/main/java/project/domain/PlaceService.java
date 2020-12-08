package project.domain;

import java.util.List;
import project.dao.PlaceDao;
import project.dao.PlaceDatabaseDao;

/**
 * Provides methods for handling Places.
 */
public class PlaceService {
    
    private PlaceDao placeDao;
    
    public PlaceService() {
        placeDao = new PlaceDatabaseDao("jdbc:sqlite:place.db");
    }
    
    public void setDatabase(PlaceDao database) {
        this.placeDao = database;
    }
    
    /**
     * Tries to create a new Place.
     * @param country the country where the place is
     * @param city the city where the place is
     * @param spot the exact spot
     * @param type the type of the place
     * @return true if a new place is created, otherwise false.
     */
    public boolean createPlace(String country, String city, String spot, String type) {
        try {   
            placeDao.addPlace(new Place(country, city, spot, type));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public void modifyPlace(int id, String country, String city, String spot, String type) throws Exception {
        placeDao.modifyPlace(id, country, city, spot, type);
    }
    
    public void removePlace(int id) throws Exception {
        placeDao.remove(id);
    }
    
    public Place getPlaceById(int id) throws Exception {
        return placeDao.findPlaceById(id);
    }
    
    public Place getPlaceByName(String name, String searchField) throws Exception {
        return placeDao.findPlaceByName(name, searchField);
    }
    
    public List<Place> getAllPlaces() throws Exception {
        return placeDao.getAllPlaces();
    }
    
    public List<Place> getPlaceBySearchTerm(String searchTerm, String searchField) throws Exception {
        return placeDao.searchPlaces(searchTerm, searchField);
    }
    
    
}
