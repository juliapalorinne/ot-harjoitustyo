
package project.domain;

import java.text.ParseException;
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


public class ObservationTest {
    
    List<StoreableObservation> observations;
    
            
    @Before
    public void setUp() throws Exception {
        observations = new ArrayList<>();
        LocalDate date = LocalDate.parse("2020-12-01");
        LocalTime time = LocalTime.parse("14:15");
        StoreableObservation obs1 = new StoreableObservation(1, 1, 1, 1, date, time, "no info", 1, "pekka");
        StoreableObservation obs2 = new StoreableObservation(2, 1, 1, 2, date, time, "no info", 0, "pekka");
        observations.add(obs1);
        observations.add(obs2);
    }
    
    @Test
    public void equalWhenSameId() throws Exception {
        LocalDate date = LocalDate.parse("2020-12-01");
        LocalTime time = LocalTime.parse("14:15");
        StoreableObservation obs1 = observations.get(0);
        StoreableObservation o = new StoreableObservation(1, 1, 1, 1, date, time, "no info", 1, "pekka");
        assertTrue(obs1.equals(o));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        StoreableObservation obs1 = observations.get(0);
        StoreableObservation obs2 = observations.get(1);
        assertFalse(obs1.equals(obs2));
    }
    
    @Test
    public void nonEqualWhenDifferentType() {
        StoreableObservation obs1 = observations.get(0);
        Object o = new Object();
        assertFalse(obs1.equals(o));
    }
    
    @Test
    public void idSetterAndGetterWork() {
        StoreableObservation obs1 = observations.get(0);
        int random = new Random().nextInt();
        obs1.setId(random);
        Assert.assertEquals(random, obs1.getId());
    }
    
    @Test
    public void speciesSetterAndGetterWork() {
        StoreableObservation obs1 = observations.get(0);
        Species species = new Species(2, "European goldfinch", "Carduelis carduelis", "tikli", "carcar");
        obs1.setSpeciesId(species.getId());
        Assert.assertEquals(2, obs1.getSpeciesId());
    }
    
    @Test
    public void individualSetterAndGetterWork() {
        StoreableObservation obs1 = observations.get(0);
        obs1.setIndividuals(3);
        Assert.assertEquals(3, obs1.getIndividuals());
    }
    
    @Test
    public void placeSetterAndGetterWork() {
        Place place = new Place(1, "Finland", "Hanko", "Tvärminne", "Shore");
        StoreableObservation obs1 = observations.get(0);
        obs1.setPlace(place.getId());
        Assert.assertEquals(1, obs1.getPlaceId());
    }
    
    @Test
    public void dateSetterAndGetterWork() throws ParseException {
        StoreableObservation obs1 = observations.get(0);
        LocalDate date = LocalDate.parse("2020-09-11");
        obs1.setDate(date);
        Assert.assertEquals(date, obs1.getDate());
    }
    
    @Test
    public void timeSetterAndGetterWork() {
        StoreableObservation obs1 = observations.get(0);
        LocalTime time = LocalTime.parse("11:30");
        obs1.setTime(time);
        Assert.assertEquals(time, obs1.getTime());
    }
    
    @Test
    public void infoSetterAndGetterWork() {
        StoreableObservation obs1 = observations.get(0);
        obs1.setInfo("in the sea");
        Assert.assertEquals("in the sea", obs1.getInfo());
    }
    
    @Test
    public void userSetterAndGetterWork() {
        StoreableObservation obs1 = observations.get(0);
        User user = new User("username", "User Name", "password");
        obs1.setUser(user.getUsername());
        Assert.assertEquals(user.getUsername(), obs1.getUserId());
    }
    

}
