
package project.domain;

import java.util.List;
import project.dao.SpeciesDao;
import project.dao.SpeciesDatabaseDao;

public class SpeciesService {
    private SpeciesDao speciesDao;
    
    public SpeciesService() {
        speciesDao = new SpeciesDatabaseDao("jdbc:sqlite:species.db");
    }
    
    public boolean createSpecies(String englishName, String scientificName, String finnishName, String abbreviation) {
        Species species = new Species(englishName, scientificName, finnishName, abbreviation);
        try {   
            speciesDao.addSpecies(species);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public void removeSpecies(String id) throws Exception {
        speciesDao.removeSpecies(id);
    }
    
    public void modifySpecies(String id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception {
        speciesDao.modifySpecies(id, englishName, scientificName, finnishName, abbreviation);
    }
    
    public Species getSpeciesById(String id) throws Exception {
        Species species = speciesDao.findSpeciesById(id);
        return species;
    }
    
    public Species getSpeciesByName(String name, String searchField) throws Exception {
        Species species = speciesDao.findSpeciesByName(name, searchField);
        return species;
    }
    
    public List<Species> getAllSpecies() throws Exception {
        return speciesDao.getAllSpecies();
    }
}
