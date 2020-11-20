
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
import project.domain.User;


public class FileUserDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File userFile;  
    UserDao dao;
    
    public FileUserDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, Exception {
        userFile = testFolder.newFile("testfile_users.txt");  
        
        try (FileWriter file = new FileWriter(userFile.getAbsolutePath())) {
            file.write("eevav;Eeva Virtanen;eevansalasana\n");
        }
        
        dao = new FileUserDao(userFile.getAbsolutePath());        
    }
    
    @Test
    public void usersAreReadCorrectlyFromFile() {
        List<User> users = dao.getAll();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("eevav", user.getUsername());
        assertEquals("Eeva Virtanen", user.getName());
        assertEquals("eevansalasana", user.getPassword());
    }
    
    @Test
    public void existingUserIsFound() {
        User user = dao.findByUsername("eevav");
        assertEquals("eevav", user.getUsername());
        assertEquals("Eeva Virtanen", user.getName());
        assertEquals("eevansalasana", user.getPassword());
    }
    
    @Test
    public void nonExistingUserIsNotFound() {
        User user = dao.findByUsername("mikkok");
        assertEquals(null, user);
    }
  
    @Test
    public void savedUserIsFound() throws Exception {
        User pekka = new User("pekka", "Pekka Mikkola", "pekansalasana");
        dao.create(pekka);
        
        User user = dao.findByUsername("pekka");
        assertEquals("pekka", user.getUsername());
        assertEquals("Pekka Mikkola", user.getName());
        assertEquals("pekansalasana", user.getPassword());
    }
    
    @After
    public void tearDown() {
        userFile.delete();
    }
}
