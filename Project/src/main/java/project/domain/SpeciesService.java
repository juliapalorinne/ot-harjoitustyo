
package project.domain;

import java.util.List;
import project.dao.SpeciesDao;
import project.dao.SpeciesDatabaseDao;

public class SpeciesService {
    
    private SpeciesDao speciesDao;
    
    public SpeciesService() {
        speciesDao = new SpeciesDatabaseDao("jdbc:sqlite:species.db");
    }
    
    public void setDatabase(SpeciesDao database) {
        this.speciesDao = database;
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
    
    
    public void modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception {
        speciesDao.modifySpecies(id, englishName, scientificName, finnishName, abbreviation);
    }
    
    public void removeSpecies(int id) throws Exception {
        speciesDao.remove(id);
    }
    
    public Species getSpeciesById(int id) throws Exception {
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
    
    public List<Species> getSpeciesBySearchTerm(String searchTerm, String searchField) throws Exception {
        return speciesDao.searchSpecies(searchTerm, searchField);
    }
    
}
