
package project.dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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


public class ObservationDaoTest { 
    
    ObservationDatabaseDao observationDao;
    File testFolder;
    private List<Species> speciesList;
    private List<Place> places;
    private List<User> users;
        
    
    @Before
    public void setUp() throws Exception {
        testFolder = new File("testFolder");
        testFolder.mkdir();
        observationDao = new ObservationDatabaseDao("jdbc:sqlite:testFolder/observation.db");
        speciesList = new ArrayList<>();
        places = new ArrayList<>();
        users = new ArrayList<>();
        
        Species s1 = new Species(14, "Eurasian wigeon", "Mareca penelope", "haapana", "marpen");
        Species s2 = new Species(196, "Bluethroat", "Luscinia svecica", "sinirinta", "lussve");
        Species s3 = new Species(39, "Eurasian wren", "Troglodytes troglodytes", "peukaloinen", "trotro");
        speciesList.add(s1);
        speciesList.add(s2);
        speciesList.add(s3);
        
        Place p1 = new Place(32, "Finland", "Kuopio", "Kallavesi", "lake");
        Place p2 = new Place(845, "Finland", "Kirkkonummi", "Porkkala", "seaside");
        Place p3 = new Place(3496, "Finland", "Espoo", "Nuuksio", "forest");
        places.add(p1);
        places.add(p2);
        places.add(p3);
        
        LocalDate date1 = LocalDate.parse("2020-05-22");
        LocalTime time1 = LocalTime.parse("16:15");
        LocalDate date2 = LocalDate.parse("2020-10-05");
        LocalTime time2 = LocalTime.parse("07:40");
        
        User u1 = new User("tanja.t", "Tanja Testaaja", "testpassword");
        User u2 = new User("keijo_k", "Keijo Kokeilija", "testpassword");
        users.add(u1);
        users.add(u2);

        observationDao.addObservation(new Observation(s1.getId(), 12, p1.getId(), date1, time1, "p", u1.getUsername()));
        observationDao.addObservation(new Observation(s1.getId(), 4, p2.getId(), date2, time1, "p", u2.getUsername()));
        observationDao.addObservation(new Observation(s3.getId(), 1, p3.getId(), date1, time2, "p", u2.getUsername()));
        observationDao.addObservation(new Observation(s3.getId(), 3, p2.getId(), date1, time1, "p", u2.getUsername()));
        observationDao.addObservation(new Observation(s2.getId(), 1, p2.getId(), date2, time2, "p", u1.getUsername()));
    }
    
    @After
    public void tearDown() {
        new File("testFolder/observation.db").delete();
        testFolder.delete();
    }
    
    
    @Test
    public void ObservationCanBeAddedToDatabaseAndFoundById() throws Exception {
        Observation o1 = observationDao.findObservationById(1);
        Observation o2 = observationDao.findObservationById(2);
        assertEquals(LocalDate.parse("2020-05-22"), o1.getDate());
        assertEquals("keijo_k", o2.getUserId());
    }
    
    @Test
    public void nonExistingObservationCanNotBeFoundById() throws Exception {
        Observation o1 = observationDao.findObservationById(18);
        assertEquals(null, o1);
    }
    
    @Test
    public void observationCanBeRemovedFromDatabase() throws Exception {
        observationDao.removeObservation(3);
        observationDao.removeObservation(1);
        assertEquals(3, observationDao.getAllObservations().size());
    }
    
    @Test
    public void observationsCanBeSearchedByDate() throws Exception {
        List<Observation> obs = observationDao.searchObservations("2020-05-22", "date");
        assertEquals(3, obs.size());
    }
    
    @Test
    public void observationsCanBeSearchedBySpecies() throws Exception {
        List<Observation> obs = observationDao.searchObservations(Integer.toString(14), "species");
        assertEquals(2, obs.size());
    }
    
    @Test
    public void observationsCanBeSearchedByPlace() throws Exception {
        List<Observation> obs = observationDao.searchObservations(Integer.toString(845), "place");
        assertEquals(3, obs.size());
    }
    
    @Test
    public void observationsCanBeSearchedByUser() throws Exception {
        List<Observation> obs = observationDao.searchObservations("tanja.t", "user");
        assertEquals(2, obs.size());
    }
    
    @Test
    public void observationsCanBeModified() throws Exception {
        LocalDate date = LocalDate.parse("2020-05-21");
        LocalTime time = LocalTime.parse("08:00");
        
        observationDao.modifyObservation(1, 12, 1, 1, date.toString(), time.toString(), "", "");
        observationDao.modifyObservation(5, 2, 2, 2, "", "", "m", "keijo_k");
        
        Observation o1 = observationDao.findObservationById(1);
        Observation o2 = observationDao.findObservationById(5);
        assertEquals(date, o1.getDate());
        assertEquals(time, o1.getTime());
        assertEquals(2, o2.getIndividuals());
        assertEquals("m", o2.getInfo());
        
        List<Observation> obs = observationDao.searchObservations("keijo_k", "user");
        assertEquals(4, obs.size());
    }
    
   
 }
