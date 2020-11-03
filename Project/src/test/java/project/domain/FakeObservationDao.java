package project.domain;

import java.util.ArrayList;
import java.util.List;
import project.dao.ObservationDao;
import project.dao.UserDao;

public class FakeObservationDao implements ObservationDao {
    List<Observation> observations = new ArrayList<>();

    
    
    public FakeObservationDao() {
        observations.add(new Observation(0, "bird", "place", null, null, "info", null));
    }
    
    
    @Override
    public Observation create(Observation obs) {
        observations.add(obs);
        return obs;
    } 
    
    @Override
    public List<Observation> getAll() {
        return observations;
    }

    @Override
    public Observation findBySpecies(String species) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Observation findByPlace(String place) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
