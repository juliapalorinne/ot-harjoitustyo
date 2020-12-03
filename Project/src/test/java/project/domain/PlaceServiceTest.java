
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
import project.dao.FakePlaceDatabaseDao;


public class PlaceServiceTest {

    private PlaceService placeService;
    private FakePlaceDatabaseDao placeDao;
    
    @Before
    public void setUp() {
        placeService = new PlaceService();
        placeDao = new FakePlaceDatabaseDao();
        placeService.setDatabase(placeDao);
        
        placeService.createPlace("Finland", "Hanko", "Halias", "bird station");
        placeService.createPlace("Finland", "Espoo", "Laajalahti, Maari", "birding tower");
    }
    
    @Test
    public void getAllReturnsAllPlaces() throws Exception {
        assertEquals(2, placeService.getAllPlaces().size());
    }
    
    @Test
    public void removePlaceRemovesPlace() throws Exception {
        placeService.removePlace(1);
        assertEquals(1, placeService.getAllPlaces().size());
    }
   
    
    @Test
    public void searchByCountryReturnsCorrectList() throws Exception {
        List<Place> pls = placeService.getPlaceBySearchTerm("Finland", "country");
        assertEquals(2, pls.size());
        
        for (Place p : pls) {
            assertEquals("Finland", p.getCountry());
        }
    }
    
    @Test
    public void searchByCityReturnsCorrectList() throws Exception {
        List<Place> pls = placeService.getPlaceBySearchTerm("Hanko", "city");
        assertEquals(1, pls.size());
        
        for (Place p : pls) {
            assertEquals("Hanko", p.getCity());
        }
    }
    
    @Test
    public void findByIdReturnsCorrectPlace() throws Exception {
        Place p = placeService.getPlaceById(2);
        assertEquals("Laajalahti, Maari", p.getSpot());
    }
    
    @Test
    public void findByNameReturnsCorrectPlace() throws Exception {
        Place p = placeService.getPlaceByName("Halias", "spot");
        assertEquals("Halias", p.getSpot());
    }
    
    

    
}

