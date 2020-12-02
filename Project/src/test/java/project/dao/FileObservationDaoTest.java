
package project.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import project.domain.Observation;
import project.domain.TestUserDao;
import project.domain.User;


public class FileObservationDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File observationFile;  
    ObservationDao observationDao;
    UserDao userDao;
    
    public FileObservationDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, Exception {
        observationFile = testFolder.newFile("testfile_observations.txt");
        
        try (FileWriter file = new FileWriter(observationFile.getAbsolutePath())) {
            file.write("10012394;common swift;48;Lohja, Mäntynummi;2020-10-22;12:00;they were flying;test_user\n");
        }
        this.userDao = new TestUserDao();
        observationDao = new FileObservationDao(observationFile.getAbsolutePath(), userDao);        
    }
    
    @Test
    public void observationsAreReadCorrectlyFromFile() {
        List<Observation> observations = observationDao.getAll();
        assertEquals(1, observations.size());
        Observation observation = observations.get(0);
        assertTrue(observation.getId().equals((long) 10012394));
        assertEquals("common swift", observation.getSpecies());
        assertEquals(48, observation.getIndividuals());
        assertEquals("Lohja, Mäntynummi", observation.getPlace());
        assertEquals("they were flying", observation.getInfo());
        assertEquals("Test User", observation.getUser().getName());
    }
    
    @Test
    public void existingObservationIsFound() {
        Observation observation = observationDao.findById((long) 10012394);
        assertTrue(observation.getId().equals((long) 10012394));
        assertEquals("common swift", observation.getSpecies());
        assertEquals(48, observation.getIndividuals());
        assertEquals("Lohja, Mäntynummi", observation.getPlace());
        assertEquals("they were flying", observation.getInfo());
        assertEquals("Test User", observation.getUser().getName());
    }
    
    @Test
    public void nonExistingObservationIsNotFound() {
        Observation observation = observationDao.findById((long) 10012734);
        assertEquals(null, observation);
    }
  
    @Test
    public void savedObservationIsFound() throws Exception {
        Observation newObs = new Observation((long) 10012738, "whinchat", 5, "Helsinki, Viikki", null, null, "no info", userDao.findByUsername("test_user"));
        observationDao.create(newObs);
        
        Observation observation = observationDao.findById((long) 10012738);
        assertTrue(observation.getId().equals((long) 10012738));
        assertEquals("whinchat", observation.getSpecies());
        assertEquals(5, observation.getIndividuals());
        assertEquals("Helsinki, Viikki", observation.getPlace());
        assertEquals("no info", observation.getInfo());
        assertEquals("Test User", observation.getUser().getName());
    }
    
    @After
    public void tearDown() {
        observationFile.delete();
    }
}
