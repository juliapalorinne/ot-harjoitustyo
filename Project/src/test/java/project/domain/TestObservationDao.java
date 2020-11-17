package project.domain;

import java.util.ArrayList;
import java.util.List;
import project.dao.ObservationDao;
import project.dao.UserDao;

public class TestObservationDao implements ObservationDao {
    List<Observation> observations = new ArrayList<>();

    
    
    public TestObservationDao() {
        observations.add(new Observation((long)0, "bird", "place", null, null, "info", null));
    }
    
    
    @Override
    public Observation create(Observation obs) {
        observations.add(obs);
        return obs;
    } 
    
    @Override
    public Observation findById(Long id) {
        for (Observation obs : observations) {
            if (obs.getId().equals(id)) {
                return obs;
            }
        }
        return null;
    }
    
    
    @Override
    public List<Observation> getAll() {
        return observations;
    }

    @Override
    public List<Observation> findBySpecies(String species) {
        List<Observation> observationsBySpecies = new ArrayList<>();
        for (Observation obs : observations) {
            if (obs.getSpecies().equals(species)) {
                observationsBySpecies.add(obs);
            }
        }
        return observationsBySpecies;
    }

    @Override
    public List<Observation> findByPlace(String place) {
        List<Observation> observationsByPlace = new ArrayList<>();
        for (Observation obs : observations) {
            if (obs.getPlace().equals(place)) {
                observationsByPlace.add(obs);
            }
        }
        return observationsByPlace;
    }



}
