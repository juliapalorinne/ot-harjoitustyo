
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


public class PlaceDaoTest { 
    
    
    PlaceDatabaseDao placeDao;
    File testFolder;
        
    
    @Before
    public void setUp() throws Exception {
        testFolder = new File("testFolder");
        testFolder.mkdir();
        placeDao = new PlaceDatabaseDao("jdbc:sqlite:testFolder/place.db");
        
        Place p1 = new Place("Finland", "Helsinki", "Fastholma", "birding tower");
        Place p2 = new Place("Finland", "Helsinki", "Lammassaari", "birding tower");
        placeDao.addPlace(p1);
        placeDao.addPlace(p2);
    }
    
    @After
    public void tearDown() {
        new File("testFolder/place.db").delete();
        testFolder.delete();
    }
    
    
    @Test
    public void placesCanBeAddedToDatabaseAndFoundById() throws Exception {
        Place p = placeDao.findPlaceById(1);
        assertEquals("Helsinki", p.getCity());
        assertEquals("Fastholma", p.getSpot());
    }
    
    @Test
    public void placesCanBeRemovedFromDatabase() throws Exception {
        placeDao.removePlace(2);
        assertEquals(1, placeDao.getAllPlaces().size());
    }
    
    @Test
    public void placesCanBeModified() throws Exception {
        placeDao.modifyPlace(2, "", "", "", "pitkospuut");
        assertEquals("pitkospuut", placeDao.findPlaceById(2).getType());
    }
    
    
   
 }
