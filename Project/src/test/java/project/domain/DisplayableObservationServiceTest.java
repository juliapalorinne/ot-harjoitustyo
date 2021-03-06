package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import project.dao.FakeObservationDatabaseDao;
import project.dao.FakePlaceDatabaseDao;
import project.dao.FakeSpeciesDatabaseDao;


public class DisplayableObservationServiceTest {

    private DisplayableObservationService dObservationService;
    private PlaceService placeService;
    private SpeciesService speciesService;
    private UserService userService;
    
    
    @Before
    public void setUp() throws Exception {
        StoreableObservationService observationService = new StoreableObservationService();
        FakeObservationDatabaseDao observationDao = new FakeObservationDatabaseDao();
        observationService.setDatabase(observationDao);
        
        placeService = new PlaceService();
        FakePlaceDatabaseDao placeDao = new FakePlaceDatabaseDao();
        placeService.setDatabase(placeDao);
        
        speciesService = new SpeciesService();
        FakeSpeciesDatabaseDao speciesDao = new FakeSpeciesDatabaseDao();
        speciesService.setDatabase(speciesDao);
        
        LocalDate date = LocalDate.parse("2020-06-01");
        LocalTime time = LocalTime.parse("04:20");
        User user = new User(1, "liisa", "Liisa", "salasana");
        observationService.setLoggedUser(user);
        
        placeService.createPlace("Finland", "Hanko", "Halias", "bird station");
        placeService.createPlace("Finland", "Espoo", "Laajalahti, Maari", "birding tower");
        
        speciesService.createSpecies("Whinchat", "Saxicola rubetra", "pensastasku", "saxrub");
        speciesService.createSpecies("Gray heron", "Ardea cinerea", "harmaahaikara", "ardcin");
        
        userService = new UserService(observationService);
        
        observationService.createObservation(speciesService.getSpeciesById(1), 2, placeService.getPlaceById(1), date, time, "p", 1);
        observationService.createObservation(speciesService.getSpeciesById(2), 2, placeService.getPlaceById(1), date, time, "p", 0);
        observationService.createObservation(speciesService.getSpeciesById(2), 2, placeService.getPlaceById(2), date, time, "p", 0);
        dObservationService = new DisplayableObservationService(observationService, speciesService, placeService, userService);
        dObservationService.redrawObservationList();
    }
    
    
    @Test
    public void getAllReturnsAllObservations() throws Exception {
        assertEquals(3, dObservationService.getAll().size());
    }
    
    @Test
    public void observationsHaveCorrectInformation() throws Exception {
        DisplayableObservation o = dObservationService.getOne(1);
        assertEquals(placeService.getPlaceById(1).toString(), o.getPlace());
        assertEquals(speciesService.getSpeciesById(1).toString(), o.getSpecies());
    }
    
    @Test
    public void getOneReturnsEmptyObservationsIfWrongId() throws Exception {
        DisplayableObservation o = dObservationService.getOne(7);
        assertEquals(null, o.getPlace());
        assertEquals(null, o.getDate());
    }
    

    
}
