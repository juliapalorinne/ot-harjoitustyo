package project.dao;

import java.util.List;
import project.domain.Species;

/**
 * SpeciesDao Interface.
 */
public interface SpeciesDao {
    
    
    /**
     * Adds new Species to database.
     * 
     * @param species new Species
     * 
     * @throws Exception Adding to database failed.
     */
    public void addSpecies(Species species) throws Exception;
    
    
    /**
     * Finds Species by id and modifies it.
     * If any of the fields is left empty, it is not modified.
     * 
     * @param id the species id
     * @param englishName English name of the species
     * @param scientificName scientific name of the species
     * @param finnishName Finnish name of the species
     * @param abbreviation 3+3 abbreviation of the species
     * 
     * @throws Exception Accessing database failed.
     */
    public void modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception;
    
    
    /**
     * Deletes a Species from the database.
     * 
     * @param id the id of the Species
     * 
     * @throws Exception Deleting from database failed.
     */
    public void remove(int id) throws Exception;
    
    
    /**
     * Returns a Species if found by id from the database.
     * @param id the species id
     * 
     * @return the list of Species
     * 
     * @throws Exception Searching database failed.
     */
    public Species findSpeciesById(int id) throws Exception;
    
    
    /**
     * Returns a Species if found by name from the database.
     * SearchField specifies the name field to search.
     * 
     * @param name the name of the wanted Species
     * @param searchField the field to search
     * 
     * @return the Species
     * 
     * @throws Exception Searching database failed.
     */
    public Species findSpeciesByName(String name, String searchField) throws Exception;

    
    /**
     * Returns all Species in the database.
     * 
     * @return the list of Species
     * 
     * @throws Exception Searching database failed.
     */
    public List<Species> getAllSpecies() throws Exception;

    
    /**
     * Returns all Species with searchTerm in searchField.
     * 
     * @param searchTerm the search term
     * @param searchField the field to search
     * 
     * @return a list of Species
     * 
     * @throws Exception Searching database failed.
     */
    public List<Species> searchSpecies(String searchTerm, String searchField) throws Exception;
}
