package project.dao;

import project.dao.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import project.domain.Observation;


public class FakeObservationDatabaseDao implements ObservationDao {

    private List<Observation> observations;
    private int id;

    public FakeObservationDatabaseDao() {
        observations = new ArrayList<>();
        id = 1;
    }
    
    
    @Override
    public void addObservation(Observation observation) throws Exception {
        observation.setId(id);
        id++;
        observations.add(observation);
    }

    
    @Override
    public List<Observation> getAllObservations() throws Exception {
        return observations;
    }
    
    
    @Override
    public void modifyObservation(int id, int species, int individuals, int place, String date, String time, String info, String username) throws Exception {

    }
    

    @Override
    public List<Observation> searchObservations(String searchTerm, String searchField) throws Exception {
        List<Observation> wantedObservations = new ArrayList<>();
        
        if (searchField.equals("date")) {
            for (Observation o : observations) {
                if (o.getDate().equals(LocalDate.parse(searchTerm))) {
                    wantedObservations.add(o);
                }
            }
        } else if (searchField.equals("species")) {
            int speciesId = Integer.parseInt(searchTerm);
            for (Observation o : observations) {
                if (o.getSpeciesId() == speciesId) {
                    wantedObservations.add(o);
                }
            }
        } else if (searchField.equals("place")) {
            int placeId = Integer.parseInt(searchTerm);
            for (Observation o : observations) {
                if (o.getPlaceId() == placeId) {
                    wantedObservations.add(o);
                }
            }
        }
        
        
        return wantedObservations;
    }
    
    
    @Override
    public Observation findObservationById(int id) throws Exception {
        for (Observation o : observations) {
            if (o.getId() == id) {
                return o;
            }
        }
        return new Observation();
    }
    

    @Override
    public void removeObservation(int id) throws Exception {
        for (int i = 0; i < observations.size(); i++) {
            if (observations.get(i).getId() == id) {
                observations.remove(i);
            }
        }
    }

}