
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
import static org.mockito.Mockito.*;
import project.dao.FakeObservationDatabaseDao;


public class ObservationServiceTest {

    private StoreableObservationService observationService;
    private FakeObservationDatabaseDao observationDao;
    private List<Place> places;
    private List<Species> speciesList;
    
    @Before
    public void setUp() {
        observationService = new StoreableObservationService();
        observationDao = new FakeObservationDatabaseDao();
        observationService.setDatabase(observationDao);
        places = new ArrayList<>();
        speciesList = new ArrayList<>();
        
        LocalDate date = LocalDate.parse("2020-06-01");
        LocalTime time = LocalTime.parse("14:20");
        User user = new User(1, "liisa", "Liisa", "salasana");
        observationService.setLoggedUser(user);
        
        Place place1 = new Place(123, "Finland", "Hanko", "Halias", "bird station");
        Place place2 = new Place(2, "Finland", "Espoo", "Laajalahti, Maari", "birding tower");
        places.add(place1);
        places.add(place2);
        Species species1 = new Species(220, "Whinchat", "Saxicola rubetra", "pensastasku", "saxrub");
        Species species2 = new Species(112, "Gray heron", "Ardea cinerea", "harmaahaikara", "ardcin");
        speciesList.add(species1);
        speciesList.add(species2);
        
        observationService.createObservation(species1, 2, place1, date, time, "p");
        observationService.createObservation(species2, 2, place1, date, time, "p");
        observationService.createObservation(species2, 2, place2, date, time, "p");
    }
    
    @Test
    public void loggedInUserCanBeSet() {
        User user = new User("alias", "Adam Smith", "newpassword");
        observationService.setLoggedUser(user);
        assertEquals(user, observationService.getLoggedUser());
    }

    @Test
    public void ifLoggedInNullGetAllReturnsEmptyList() throws Exception {
        observationService.setLoggedUser(null);
        assertEquals(new ArrayList<StoreableObservation>(), observationService.getAll());
    }
    
    
    @Test
    public void getAllReturnsAllObservations() throws Exception {
        assertEquals(3, observationService.getAll().size());
    }
    
    @Test
    public void removeObservationRemovesObservation() throws Exception {
        observationService.removeObservation(1);
        assertEquals(2, observationService.getAll().size());
    }
   
    
    @Test
    public void searchBySpeciesReturnsCorrectList() throws Exception {
        String id = Integer.toString(speciesList.get(1).getId());
        List<StoreableObservation> obs = observationService.getObservationsBySearchTerm(id, "species");
        assertEquals(2, obs.size());
        
        for (StoreableObservation o : obs) {
            assertEquals(112, o.getSpeciesId());
        }
    }
    
    @Test
    public void searchByPlaceReturnsCorrectList() throws Exception {
        String id = Integer.toString(places.get(0).getId());
        List<StoreableObservation> obs = observationService.getObservationsBySearchTerm(id, "place");
        assertEquals(2, obs.size());
        
        for (StoreableObservation o : obs) {
            assertEquals(123, o.getPlaceId());
        }
    }
    
    @Test
    public void findByIdReturnsCorrectObservation() throws Exception {
        StoreableObservation o = observationService.findObservationById(1);
        assertEquals(220, o.getSpeciesId());
    }
    
    
    

    
}
