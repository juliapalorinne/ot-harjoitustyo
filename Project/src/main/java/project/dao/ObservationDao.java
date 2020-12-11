
package project.dao;

import java.util.List;
import project.domain.StoreableObservation;


public interface ObservationDao {
    
    public void addObservation(StoreableObservation observation) throws Exception;
     
    public void modifyObservation(int id, int species, int individuals, int place,
            String date, String time, String info, int privacy, String username) throws Exception;
    
    public void remove(int id) throws Exception;
    
    public StoreableObservation findObservationById(int id) throws Exception;

    public List<StoreableObservation> getAllObservations() throws Exception;
    
    public List<StoreableObservation> searchObservations(String searchTerm, String searchField) throws Exception;
    
}
