
package project.domain;

import java.util.List;
import project.dao.PlaceDao;
import project.dao.PlaceDatabaseDao;

public class PlaceService {
    private PlaceDao placeDao;
    
    public PlaceService() {
        placeDao = new PlaceDatabaseDao("jdbc:sqlite:place.db");
    }
    
    
    public void setDatabase(PlaceDao database) {
        placeDao = database;
    }
    
    public boolean createPlace(String country, String city, String spot, String type) {
        Place place = new Place(country, city, spot, type);
        try {   
            placeDao.addPlace(place);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public void removePlace(int id) throws Exception {
        placeDao.removePlace(id);
    }
    
    public void modifyPlace(int id, String country, String city, String spot, String type) throws Exception {
        placeDao.modifyPlace(id, country, city, spot, type);
    }
    
    public Place getPlaceById(int id) throws Exception {
        Place place = placeDao.findPlaceById(id);
        return place;
    }
    
    public Place getPlaceByName(String name, String searchField) throws Exception {
        Place place = placeDao.findPlaceByName(name, searchField);
        return place;
    }
    
    public List<Place> getAllPlaces() throws Exception {
        return placeDao.getAllPlaces();
    }
    
    public List<Place> getPlaceBySearchTerm(String searchTerm, String searchField) throws Exception {
         return placeDao.searchPlaces(searchTerm, searchField);
    }
}
