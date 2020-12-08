package project.dao;

import java.io.File;
import java.util.List;
import project.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


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
        Place p3 = new Place("Finland", "Espoo", "Maari", "birding tower");
        placeDao.addPlace(p1);
        placeDao.addPlace(p2);
        placeDao.addPlace(p3);
    }
    
    @After
    public void tearDown() {
        new File("testFolder/place.db").delete();
        testFolder.delete();
    }
    
    
    @Test
    public void getAllReturnsAllPlaces() throws Exception {
        assertEquals(3, placeDao.getAllPlaces().size());
    }
    
    @Test
    public void placesCanBeAddedToDatabaseAndFoundById() throws Exception {
        Place p = placeDao.findPlaceById(1);
        assertEquals("Helsinki", p.getCity());
        assertEquals("Fastholma", p.getSpot());
    }
    
    @Test
    public void nonExistingPlaceCanNotBeFoundById() throws Exception {
        Place p = placeDao.findPlaceById(5);
        assertEquals(null, p);
    }
    
    @Test
    public void placesCanBeRemovedFromDatabase() throws Exception {
        placeDao.remove(2);
        assertEquals(2, placeDao.getAllPlaces().size());
    }
    
    @Test
    public void placesCanBeModified() throws Exception {
        placeDao.modifyPlace(2, "", "", "", "pitkospuut");
        placeDao.modifyPlace(1, "", "Hanko", "Halias", "");
        assertEquals("pitkospuut", placeDao.findPlaceById(2).getType());
        assertEquals("Hanko", placeDao.findPlaceById(1).getCity());
    }
    
    @Test
    public void placesCanBeSearchedByField() throws Exception {
        List<Place> places = placeDao.searchPlaces("Helsinki", "city");
        assertEquals(2, places.size());
    }
    
    @Test
    public void placeCanBeFoundByName() throws Exception {
        Place place = placeDao.findPlaceByName("Maari", "spot");
        assertEquals("birding tower", place.getType());
    }
    
    
   
 }
