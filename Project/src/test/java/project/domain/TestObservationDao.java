package project.domain;

import java.util.ArrayList;
import java.util.List;
import project.dao.ObservationDao;
import project.dao.UserDao;

public class TestObservationDao implements ObservationDao {
    List<Observation> observations = new ArrayList<>();

    
    
    public TestObservationDao() {
        // observations.add(new Observation((long)0, "bird", 1, "place", null, null, "info", null));
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
        observations.stream().filter((obs) -> (obs.getSpecies().equals(species))).forEachOrdered((obs) -> {
            observationsBySpecies.add(obs);
        });
        return observationsBySpecies;
    }

    @Override
    public List<Observation> findByPlace(String place) {
        List<Observation> observationsByPlace = new ArrayList<>();
        observations.stream().filter((obs) -> (obs.getPlace().equals(place))).forEachOrdered((obs) -> {
            observationsByPlace.add(obs);
        });
        return observationsByPlace;
    }



}
