
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
import project.dao.TestPlaceDatabaseDao;
import project.dao.TestSpeciesDatabaseDao;
import project.dao.TestUserDatabaseDao;


public class ObservationTest {
    TestUserDatabaseDao userDao;
    TestPlaceDatabaseDao placeDao;
    TestSpeciesDatabaseDao speciesDao;
    
    List<Observation> observations;
    
            
    @Before
    public void setUp() throws Exception {
        this.userDao = new TestUserDatabaseDao("jdbc:sqlite:testUser.db");
        this.placeDao = new TestPlaceDatabaseDao("jdbc:sqlite:testPlace.db");
        this.speciesDao = new TestSpeciesDatabaseDao("jdbc:sqlite:testSpecies.db");
        
        Place place1 = new Place("Finland", "Helsinki", "Viikki", "Shore");
        Place place2 = new Place("Finland", "Helsinki", "Kalasatama", "City");
        placeDao.addPlace(place1);
        placeDao.addPlace(place2);
        
        Species species = new Species("crow", "Corvus corone cornix", "varis", "cornix");
        speciesDao.addSpecies(species);
        
        User user = new User("matti", "Matti", "salasana");
        userDao.addUser(user);
        
        Observation obs1 = new Observation(1, speciesDao.findSpeciesByName("crow", "englishName").getId(), 1, placeDao.findPlaceByName("Viikki", "spot").getId(), null, null, null, user.getUsername());
        Observation obs2 = new Observation(2, speciesDao.findSpeciesByName("crow", "englishName").getId(), 1, placeDao.findPlaceByName("Kalasatama", "spot").getId(), null, null, null, user.getUsername());
        observations.add(obs1);
        observations.add(obs2);
    }
    
    @Test
    public void equalWhenSameId() throws Exception {
        Observation o = observations.get(0);
        Observation obs1 = new Observation(1, speciesDao.findSpeciesByName("crow", "englishName").getId(), 1, placeDao.findPlaceByName("Viikki", "spot").getId(), null, null, null, userDao.findUserByName("Matti", "name").getUsername());
        assertTrue(o.equals(obs1));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        Observation obs1 = observations.get(0);
        Observation obs2 = observations.get(1);
        assertFalse(obs1.equals(obs2));
    }
    
    @Test
    public void nonEqualWhenDifferentType() {
        Observation obs1 = observations.get(0);
        Object o = new Object();
        assertFalse(obs1.equals(o));
    }
    
    @Test
    public void idSetterAndGetterWork() {
        Observation obs1 = observations.get(0);
        int random = new Random().nextInt();
        obs1.setId(random);
        Assert.assertEquals(random, obs1.getId());
    }
    
    @Test
    public void speciesSetterAndGetterWork() {
        Observation obs1 = observations.get(0);
        Species species = new Species(2, "European goldfinch", "Carduelis carduelis", "tikli", "carcar");
        obs1.setSpeciesId(species.getId());
        Assert.assertEquals(2, obs1.getSpeciesId());
    }
    
    @Test
    public void individualSetterAndGetterWork() {
        Observation obs1 = observations.get(0);
        obs1.setIndividuals(3);
        Assert.assertEquals(3, obs1.getIndividuals());
    }
    
    @Test
    public void placeSetterAndGetterWork() {
        Place place = new Place(1, "Finland", "Hanko", "Tvärminne", "Shore");
        Observation obs1 = observations.get(0);
        obs1.setPlace(place.getId());
        Assert.assertEquals(1, obs1.getPlaceId());
    }
    
    @Test
    public void dateSetterAndGetterWork() throws ParseException {
        Observation obs1 = observations.get(0);
        LocalDate date = LocalDate.parse("2020-09-11");
        obs1.setDate(date);
        Assert.assertEquals(date, obs1.getDate());
    }
    
    @Test
    public void timeSetterAndGetterWork() {
        Observation obs1 = observations.get(0);
        LocalTime time = LocalTime.parse("11:30");
        obs1.setTime(time);
        Assert.assertEquals(time, obs1.getTime());
    }
    
    @Test
    public void infoSetterAndGetterWork() {
        Observation obs1 = observations.get(0);
        obs1.setInfo("in the sea");
        Assert.assertEquals("in the sea", obs1.getInfo());
    }
    
    @Test
    public void userSetterAndGetterWork() {
        Observation obs1 = observations.get(0);
        User user = new User("username", "User Name", "password");
        obs1.setUser(user.getUsername());
        Assert.assertEquals(user.getUsername(), obs1.getUserId());
    }


}
