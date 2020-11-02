
package project.dao;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import project.domain.Observation;


public interface ObservationDao {

    Observation create(Observation todo) throws Exception;

    List<Observation> getAll();
    
    public Observation findBySpecies(String species);
    
    public Observation findByPlace(String place);
    
}
