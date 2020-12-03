
package project.domain;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import project.dao.FakeSpeciesDatabaseDao;


public class SpeciesServiceTest {

    private SpeciesService speciesService;
    private FakeSpeciesDatabaseDao speciesDao;
    
    @Before
    public void setUp() {
        speciesService = new SpeciesService();
        speciesDao = new FakeSpeciesDatabaseDao();
        speciesService.setDatabase(speciesDao);
        
        speciesService.createSpecies("Whinchat", "Saxicola rubetra", "pensastasku", "saxrub");
        speciesService.createSpecies("Gray heron", "Ardea cinerea", "harmaahaikara", "ardcin");
    }
    
    
    @Test
    public void getAllReturnsAllSpecies() throws Exception {
        assertEquals(2, speciesService.getAllSpecies().size());
    }
    
    @Test
    public void removeObservationRemovesObservation() throws Exception {
        speciesService.removeSpecies(1);
        assertEquals(1, speciesService.getAllSpecies().size());
    }
   
    
    @Test
    public void searchByEnglishNameReturnsCorrectList() throws Exception {
        List<Species> species = speciesService.getSpeciesBySearchTerm("chat", "englishName");
        assertEquals(1, species.size());
        
        for (Species s : species) {
            assertEquals("Whinchat", s.getEnglishName());
        }
    }
    
    @Test
    public void searchByScientificNameReturnsCorrectList() throws Exception {
        List<Species> species = speciesService.getSpeciesBySearchTerm("Ardea", "scientificName");
        assertEquals(1, species.size());
        
        for (Species s : species) {
            assertEquals("Ardea cinerea", s.getScientificName());
        }
    }
    
    @Test
    public void searchByFullEnglishNameReturnsCorrectSpecies() throws Exception {
        Species s = speciesService.getSpeciesByName("Whinchat", "englishName");
        assertEquals("Whinchat", s.getEnglishName());
    }
    
    @Test
    public void searchByFullScientificNameReturnsCorrectSpecies() throws Exception {
        Species s = speciesService.getSpeciesByName("Ardea cinerea", "scientificName");
        assertEquals("Ardea cinerea", s.getScientificName());
    }
    

    
}
