
package project.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ObservationServiceTest {

    ObservationService observationService;
    
    @Before
    public void setUp() {
        observationService = new ObservationService();
        observationService.setDatabase("jdbc:sqlite:testObservation.db");
        User user = new User("janedoe", "Jane Doe", "janespassword");
        observationService.setLoggedUser(user);
    }
    
    @Test
    public void loggedInUserCanBeSet() {
        User user = new User("alias", "Adam Smith", "newpassword");
        observationService.setLoggedUser(user);
        assertEquals(user, observationService.getLoggedUser());
    }
    
    
//    @Test
//    public void userCanCreateNewObservations() {
//        int numberOfObservationsBefore = observationService.getAll().size();
//        observationService.createObservation("crow", 1, "Helsinki", null, null, null);
//        observationService.createObservation();
//        int numberOfObservationsAfter = observationService.getAll().size();
//        assertEquals(numberOfObservationsBefore+2, numberOfObservationsAfter);
//    }
//    
//    
//    @Test
//    public void getAllListsAllObservations() {
//        observationService.createObservation("magpie", 1, "Helsinki", null, null, null);
//        observationService.createObservation("magpie", 1, "Turku", null, null, null);
//        observationService.createObservation("magpie", 1, "Turku", null, null, null);
//        observationService.createObservation("swan", 12, "Hanko", null, null, null);
//        
//        int numberOfMagpies = 0;
//        int numberOfIndividuals = 0;
//        int observationsInTurku = 0;
//        for (Observation o : observationService.getAll()) {
//            if (o.getPlace().equals("Turku"))
//                observationsInTurku++;
//            if (o.getSpecies().equals("magpie"))
//                numberOfMagpies += o.getIndividuals();
//            numberOfIndividuals += o.getIndividuals();
//        }
//        
//        assertEquals(3, numberOfMagpies);
//        assertEquals(15, numberOfIndividuals);
//        assertEquals(2, observationsInTurku);
//        assertEquals(4, observationService.getAll().size());
//    }  
    
    @Test
    public void ifLoggedInNullGetAllReturnsEmptyList() throws Exception {
        observationService.setLoggedUser(null);
        assertEquals(new ArrayList<Observation>(), observationService.getAll());
    }
    
    

    
}
