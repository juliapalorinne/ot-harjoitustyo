package project.dao;

import java.util.List;
import project.domain.Species;

/**
 * SpeciesDao Interface.
 */
public interface SpeciesDao {
    
    public void addSpecies(Species species) throws Exception;
    
    public void modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception;
    
    public void remove(int id) throws Exception;
    
    public Species findSpeciesById(int id) throws Exception;
    
    public Species findSpeciesByName(String name, String searchField) throws Exception;

    public List<Species> getAllSpecies() throws Exception;

    public List<Species> searchSpecies(String searchTerm, String searchField) throws Exception;
}
