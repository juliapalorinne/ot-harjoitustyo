package project.dao;

import project.dao.*;
import java.util.ArrayList;
import java.util.List;
import project.domain.Species;


public class FakeSpeciesDatabaseDao implements SpeciesDao {

    private List<Species> speciesList;
    private int id;

    public FakeSpeciesDatabaseDao() {
        speciesList = new ArrayList<>();
        id = 1;
    }
    
    
    @Override
    public void addSpecies(Species species) throws Exception {
        species.setId(id);
        id++;
        speciesList.add(species);
    }

    
    @Override
    public List<Species> getAllSpecies() throws Exception {
        return speciesList;
    }
    
    
    @Override
    public void modifySpecies(int id, String englishName, String scientificName, String finnishName, String abbreviation) throws Exception {
       
    }
    

    @Override
    public List<Species> searchSpecies(String searchTerm, String searchField) throws Exception {
        List<Species> wantesSpecies = new ArrayList<>();
        
        if (searchField.equals("englishName")){
            speciesList.stream().filter((p) -> (p.getEnglishName().contains(searchTerm))).forEachOrdered((p) -> {
                wantesSpecies.add(p);
            });
        } else if (searchField.equals("scientificName")) {
            speciesList.stream().filter((p) -> (p.getScientificName().contains(searchTerm))).forEachOrdered((p) -> {
                wantesSpecies.add(p);
            });
        } else if (searchField.equals("finnishName")) {
            speciesList.stream().filter((p) -> (p.getFinnishName().contains(searchTerm))).forEachOrdered((p) -> {
                wantesSpecies.add(p);
            });
        }    
        return wantesSpecies;
    }
    
    
    @Override
    public Species findSpeciesById(int id) throws Exception {
       for (Species s : speciesList) {
            if (s.getId() == id) {
                return s;
            }
        }
        return new Species();
    }

    
    @Override
    public Species findSpeciesByName(String name, String searchField) throws Exception {
        
        for (Species s : speciesList) {
            if (searchField.equals("englishName")) {
                if (s.getEnglishName().equals(name)) {
                    return s;
                }
            } else if (searchField.equals("scientificName")) {
                if (s.getScientificName().equals(name)) {
                    return s;
                }
            } else if (searchField.equals("finnishName"))  {
                if (s.getFinnishName().equals(name)) {
                    return s;
                }
            }
        }
        
        return new Species();
    }


    

    @Override
    public void removeSpecies(int id) throws Exception {
        speciesList.remove(id);
    }
    
    

}
