package project.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import project.domain.StoreableObservation;


public class FakeObservationDatabaseDao implements ObservationDao {

    private List<StoreableObservation> observations;
    private int id;

    public FakeObservationDatabaseDao() {
        observations = new ArrayList<>();
        id = 1;
    }
    
    
    @Override
    public void addObservation(StoreableObservation observation) throws Exception {
        observation.setId(id);
        id++;
        observations.add(observation);
    }

    
    @Override
    public List<StoreableObservation> getAllObservations() throws Exception {
        return observations;
    }
    
    
    @Override
    public void modifyObservation(int id, int species, int individuals, int place, String date, String time, String info, String username) throws Exception {

    }
    

    @Override
    public List<StoreableObservation> searchObservations(String searchTerm, String searchField) throws Exception {
        List<StoreableObservation> wantedObservations = new ArrayList<>();
        
        if (searchField.equals("date")) {
            for (StoreableObservation o : observations) {
                if (o.getDate().equals(LocalDate.parse(searchTerm))) {
                    wantedObservations.add(o);
                }
            }
        } else if (searchField.equals("species")) {
            int speciesId = Integer.parseInt(searchTerm);
            for (StoreableObservation o : observations) {
                if (o.getSpeciesId() == speciesId) {
                    wantedObservations.add(o);
                }
            }
        } else if (searchField.equals("place")) {
            int placeId = Integer.parseInt(searchTerm);
            for (StoreableObservation o : observations) {
                if (o.getPlaceId() == placeId) {
                    wantedObservations.add(o);
                }
            }
        }
        
        
        return wantedObservations;
    }
    
    
    @Override
    public StoreableObservation findObservationById(int id) throws Exception {
        for (StoreableObservation o : observations) {
            if (o.getId() == id) {
                return o;
            }
        }
        return new StoreableObservation();
    }
    

    @Override
    public void remove(int id) throws Exception {
        for (int i = 0; i < observations.size(); i++) {
            if (observations.get(i).getId() == id) {
                observations.remove(i);
            }
        }
    }

}
