
package project.dao;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import project.domain.Observation;
import project.domain.Species;


public interface SpeciesDao {

    public Species create(Species species) throws Exception;
    
    public Species findById(Long id);
    
    public Species findByEnglishName(String name);
    
    public Species findByScientificName(String name);
    
    public Species findByFinnishName(String name);
    
    public Species findByAbbreviation(String abbreviation);

    public List<Species> getAll();
    
}
