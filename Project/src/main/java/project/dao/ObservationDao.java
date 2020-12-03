
package project.dao;

import java.util.List;
import project.domain.Observation;


public interface ObservationDao {
    
    public void addObservation(Observation observation) throws Exception;
    
    public void removeObservation(int id) throws Exception;
    
    public void modifyObservation(int id, int species, int individuals, int place,
            String date, String time, String info, String username) throws Exception;
    
    public Observation findObservationById(int id) throws Exception;

    public List<Observation> getAllObservations() throws Exception;
    
    public List<Observation> searchObservations(String searchTerm, String searchField) throws Exception;
    
}
