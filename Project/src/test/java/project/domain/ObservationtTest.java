
package project.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
        observationDao.create(new Observation((long) 1, "crow", 1, "Helsinki", null, null, null, null));
        observationDao.create(new Observation((long) 2, "magpie", 1, "Turku", null, null, null, null));
        observationDao.create(new Observation((long) 3, "magpie", 1, "Helsinki", null, null, null, null));
        
        
        obs1 = new Observation((long) 1, "crow", 1, "Helsinki", null, null, null, null);
        obs2 = new Observation((long) 2, "magpie", 1, "Turku", null, null, null, null);
        obs3 = new Observation((long) 3, "magpie", 1, "Helsinki", null, null, null, null);
    }
    
    @Test
    public void equalWhenSameId() {
        Long id = (long) 1;
        assertTrue(this.observationDao.findById(id).equals(new Observation((long)1, "crow", 1, "Helsinki", null, null, null, null)));
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
    
    @Test
    public void idSetterAndGetterWork() {
        long random = new Random().nextLong();
        obs1.setId(random);
        assertTrue(obs1.getId().equals(random));
    }
    
    @Test
    public void speciesSetterAndGetterWork() {
        obs1.setSpecies("smew");
        Assert.assertEquals("smew", obs1.getSpecies());
    }
    
    @Test
    public void individualSetterAndGetterWork() {
        obs1.setIndividuals(3);
        Assert.assertEquals(3, obs1.getIndividuals());
    }
    
    @Test
    public void placeSetterAndGetterWork() {
        obs1.setPlace("Hanko");
        Assert.assertEquals("Hanko", obs1.getPlace());
    }
    
    @Test
    public void dateSetterAndGetterWork() throws ParseException {
        LocalDate date = LocalDate.parse("2020-09-11");
        obs1.setDate(date);
        Assert.assertEquals(date, obs1.getDate());
    }
    
    @Test
    public void timeSetterAndGetterWork() {
        LocalTime time = LocalTime.parse("11:30");
        obs1.setTime(time);
        Assert.assertEquals(time, obs1.getTime());
    }
    
    @Test
    public void infoSetterAndGetterWork() {
        obs1.setInfo("in the sea");
        Assert.assertEquals("in the sea", obs1.getInfo());
    }
    
    @Test
    public void userSetterAndGetterWork() {
        User user = new User("username", "User Name", "password");
        obs1.setUser(user);
        Assert.assertEquals(user, obs1.getUser());
    }
    
    
    
    @Test
    public void findBySpeciesReturnsObservationsWithSameSpecies() {
        List<Observation> o = new ArrayList<>();
        o.add(obs2);
        o.add(obs3);
        o.sort(Comparator.comparingLong(Observation::getId));
        
        List<Observation> findBySpecies = observationDao.findBySpecies("magpie");
        findBySpecies.sort(Comparator.comparingLong(Observation::getId));

        Assert.assertEquals(o, findBySpecies);
    }

    @Test
    public void findByPlaceReturnsObservationsWithSamePlace() {
        List<Observation> o = new ArrayList<>();
        o.add(obs1);
        o.add(obs3);
        o.sort(Comparator.comparingLong(Observation::getId));
        
        List<Observation> findByPlace = observationDao.findByPlace("Helsinki");
        findByPlace.sort(Comparator.comparingLong(Observation::getId));

        Assert.assertEquals(o, findByPlace);
    }

}
