
package project.dao;

import java.io.File;
import java.io.IOException;
import project.domain.*;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class SpeciesDaoTest { 
    
    SpeciesDatabaseDao speciesDao;
    File testFolder;
        
    
    @Before
    public void setUp() throws Exception {
        testFolder = new File("testFolder");
        testFolder.mkdir();
        speciesDao = new SpeciesDatabaseDao("jdbc:sqlite:testFolder/species.db");
        
        speciesDao.addSpecies(new Species("Eurasian wigeon", "Mareca penelope", "haapana", "marpen"));
        speciesDao.addSpecies(new Species("Common crane", "Grus grus", "kurki", "grugru"));
        speciesDao.addSpecies(new Species("Arctic loon", "Gavia arctica", "kuikka", "gavarc"));
        speciesDao.addSpecies(new Species("Eurasian nuthatch", "Sitta europaea", "pähkinänakkeli", "siteur"));
        speciesDao.addSpecies(new Species("Bluethroat", "Luscinia svecica", "sinirinta", "lussve"));
        speciesDao.addSpecies(new Species("Eurasian treecreeper", "Certhia familiaris", "puukiipijä", "cerfam"));
        speciesDao.addSpecies(new Species("Eurasian wren", "Troglodytes troglodytes", "peukaloinen", "trotro"));
    }
    
    @After
    public void tearDown() {
        new File("testFolder/species.db").delete();
        testFolder.delete();
    }
    
    
    @Test
    public void speciesCanBeAddedToDatabaseAndFoundById() throws Exception {
        Species s1 = speciesDao.findSpeciesById(2);
        Species s2 = speciesDao.findSpeciesById(3);
        assertEquals("kurki", s1.getFinnishName());
        assertEquals("Arctic loon", s2.getEnglishName());
    }
    
    @Test
    public void nonExistingSpeciesCanNotBeFoundById() throws Exception {
        Species s = speciesDao.findSpeciesById(42);
        assertEquals(null, s);
    }
    
    @Test
    public void speciesCanBeRemovedFromDatabase() throws Exception {
        speciesDao.removeSpecies(6);
        assertEquals(6, speciesDao.getAllSpecies().size());
    }
    
    @Test
    public void placesCanBeModified() throws Exception {
        speciesDao.modifySpecies(2, "", "", "", "ggru");
        assertEquals("ggru", speciesDao.findSpeciesById(2).getAbbreviation());
    }
    
    
   
 }
