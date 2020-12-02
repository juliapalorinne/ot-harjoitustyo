
package project.domain;

import java.util.List;
import java.util.stream.Collectors;
import project.dao.SpeciesDao;

public class SpeciesService {
    private SpeciesDao speciesDao;
    
    public SpeciesService(SpeciesDao speciesDao) {
        this.speciesDao = speciesDao;
    }
    
    public boolean createSpecies(String englishName, String scientificName, String finnishName, String abbreviation) {
        Species species = new Species(englishName, scientificName, finnishName, abbreviation);
        try {   
            speciesDao.create(species);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public List<Species> getAll() {
        return speciesDao.getAll()
            .stream()
            .collect(Collectors.toList());
    }
     
}
