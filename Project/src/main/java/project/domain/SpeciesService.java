
package project.domain;

import java.util.List;
import project.dao.SpeciesDao;
import project.dao.SpeciesDatabaseDao;

/**
 * Provides methods for handling Species.
 */
public class SpeciesService {
    
    private SpeciesDao speciesDao;
    
    /**
     * Starts a SpeciesService with given database address.
     */
    public SpeciesService() {
        speciesDao = new SpeciesDatabaseDao("jdbc:sqlite:species.db");
    }
    
    
    /**
     * Sets the database for Species.
     * 
     * @param database database
     */
    public void setDatabase(SpeciesDao database) {
        this.speciesDao = database;
    }
    
    
    /**
     * Tries to create a new Species.
     * 
     * @param englishName the English name
     * @param scientificName the scientific name
     * @param finnishName the Finnish name
     * @param abbreviation 3+3 abbreviation
     * 
     * @return true if Species id created, otherwise false.
     */
    public boolean createSpecies(String englishName, String scientificName, String finnishName, String abbreviation) {
        Species species = new Species(englishName, scientificName, finnishName, abbreviation);
        try {   
            speciesDao.addSpecies(species);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Tries to modify the Species.
     * 
     * @param id id 
     * @param englishName the English name
     * @param scientificName the scientific name
     * @param finnishName the Finnish name
     * @param abbreviation 3+3 abbreviation
     * 
     * @return true if Species id modified, otherwise false.
     * 
     * @throws Exception Modifying Species failed.
     */
    public boolean modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception {
        try {   
            speciesDao.modifySpecies(id, englishName, scientificName, finnishName, abbreviation);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Tries to delete the Species with given id.
     * 
     * @param id id
     * 
     * @throws Exception Removing Species failed.
     */
    public void removeSpecies(int id) throws Exception {
        speciesDao.remove(id);
    }
    
    
    /**
     * Tries to find a Species by id.
     * 
     * @param id id
     * 
     * @return the Species
     * 
     * @throws Exception Searching Species failed.
     */
    public Species getSpeciesById(int id) throws Exception {
        Species species = speciesDao.findSpeciesById(id);
        return species;
    }
    
    
    /**
     * Tries to find a Species by name.
     * 
     * @param name name
     * @param searchField the field to search
     * 
     * @return the Species
     * 
     * @throws Exception Searching Species failed.
     */
    public Species getSpeciesByName(String name, String searchField) throws Exception {
        Species species = speciesDao.findSpeciesByName(name, searchField);
        return species;
    }
    
    
    /**
     * Tries to get the list of all Species.
     * 
     * @return the list of all Species
     * 
     * @throws Exception Listing all Species failed.
     */
    public List<Species> getAllSpecies() throws Exception {
        return speciesDao.getAllSpecies();
    }
    
    
    /**
     * Finds list of Species by search term.
     * 
     * @param searchTerm the term to search
     * @param searchField the field to search
     * 
     * @return the list of Species
     * 
     * @throws Exception Listing the Species failed.
     */
    public List<Species> getSpeciesBySearchTerm(String searchTerm, String searchField) throws Exception {
        return speciesDao.searchSpecies(searchTerm, searchField);
    }
    
}
