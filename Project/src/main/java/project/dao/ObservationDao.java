
package project.dao;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import project.domain.Observation;


public interface ObservationDao {

    public Observation create(Observation todo) throws Exception;
    
    public Observation findById(Long id);

    public List<Observation> getAll();
    
    public List<Observation> findBySpecies(String species);
    
    public List<Observation> findByPlace(String place);
    
}
