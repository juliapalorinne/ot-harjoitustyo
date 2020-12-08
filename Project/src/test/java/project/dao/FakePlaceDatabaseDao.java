package project.dao;

import java.util.ArrayList;
import java.util.List;
import project.domain.Place;


public class FakePlaceDatabaseDao implements PlaceDao {

    private List<Place> places;
    private int id;

    public FakePlaceDatabaseDao() {
        places = new ArrayList<>();
        id = 1;
    }
    
    
    @Override
    public void addPlace(Place place) throws Exception {
        place.setId(id);
        id++;
        places.add(place);
    }

    
    @Override
    public List<Place> getAllPlaces() throws Exception {
        return places;
    }
    
    
    @Override
    public void modifyPlace(int id, String country, String city, String spot, String type) throws Exception {
    }
    

    @Override
    public List<Place> searchPlaces(String searchTerm, String searchField) throws Exception {
        List<Place> wantedPlaces = new ArrayList<>();
        
        if (searchField.equals("country")) {
            places.stream().filter((p) -> (p.getCountry().equals(searchTerm))).forEachOrdered((p) -> {
                wantedPlaces.add(p);
            });
        } else if (searchField.equals("city")) {
            places.stream().filter((p) -> (p.getCity().equals(searchTerm))).forEachOrdered((p) -> {
                wantedPlaces.add(p);
            });
        } else if (searchField.equals("spot")) {
            places.stream().filter((p) -> (p.getSpot().equals(searchTerm))).forEachOrdered((p) -> {
                wantedPlaces.add(p);
            });
        }    
        return wantedPlaces;
    }
    
    
    @Override
    public Place findPlaceById(int id) throws Exception {
        for (Place p : places) {
            if (p.getId() == id) {
                return p;
            }
        }
        return new Place();
    }

    
    @Override
    public Place findPlaceByName(String name, String searchField) throws Exception {
        List<Place> pls = searchPlaces(name, searchField);
        if (pls.size() == 1) {
            return pls.get(0);
        }
        return new Place();
    }


    

    @Override
    public void remove(int id) throws Exception {
        for (Place p : places) {
            if (p.getId() == id) {
                places.remove(p);
            }
        }
    }
    
}
    
    