
package project.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ObservationtTest {
    private TestObservationDao observationDao;
    private Observation obs1;
    private Observation obs2;
    private Observation obs3;
    
            
    @Before
    public void setUp() {
        observationDao = new TestObservationDao();
        observationDao.create(new Observation((long) 1, "crow", "Helsinki", null, null, null, null));
        observationDao.create(new Observation((long) 2, "magpie", "Turku", null, null, null, null));
        observationDao.create(new Observation((long) 3, "magpie", "Helsinki", null, null, null, null));
        
        
        obs1 = new Observation((long) 1, "crow", "Helsinki", null, null, null, null);
        obs2 = new Observation((long) 2, "magpie", "Turku", null, null, null, null);
        obs3 = new Observation((long) 3, "magpie", "Helsinki", null, null, null, null);
    }
    
    @Test
    public void equalWhenSameId() {
        Long id = (long) 1;
        assertTrue(this.observationDao.findById(id).equals(new Observation((long)1, "crow", "Helsinki", null, null, null, null)));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        assertFalse(obs1.equals(obs2));
    }
    
    @Test
    public void nonEqualWhenDifferentType() {
        Object o = new Object();
        assertFalse(obs1.equals(o));
    }
//    
//    @Test
//    public void findBySpeciesReturnsObservationsWithSameSpecies() {
//        List<Observation> o = new ArrayList<>();
//        o.add(new Observation((long) 2, "magpie", "Turku", null, null, null, null));
//        o.add(new Observation((long) 3, "magpie", "Helsinki", null, null, null, null));
//        
//        
//        List<Observation> findBySpecies = observationDao.findBySpecies("magpie");
//        assertTrue(findBySpecies.toString().equals(o.toString()));
//    }
//
//    @Test
//    public void findByPlaceReturnsObservationsWithSamePlace() {
//        List<Observation> o = new ArrayList<>();
//        o.add(new Observation((long) 1, "crow", "Helsinki", null, null, null, null));
//        o.add(new Observation((long) 3, "magpie", "Helsinki", null, null, null, null));
//        
//        List<Observation> findByPlace = observationDao.findByPlace("Helsinki");
//        assertTrue(findByPlace.toString().equals(o.toString()));
//    }

}
