package project.dao;

import java.util.List;
import project.domain.Place;

/**
 * PlaceDao Interface.
 */
public interface PlaceDao {
    
    public void addPlace(Place place) throws Exception;
    
    public void modifyPlace(String id, String country, String city, String spot, String type) throws Exception;
    
    public void removePlace(String id) throws Exception;
    
    public Place findPlaceById(String id) throws Exception;
    
    public Place findPlaceByName(String name, String searchField) throws Exception;

    public List<Place> getAllPlaces() throws Exception;

    public List<Place> searchPlaces(String searchTerm, String searchField) throws Exception;
}
