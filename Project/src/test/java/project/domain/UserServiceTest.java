
package project.domain;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserServiceTest {

    UserService userService;
    ObservationService observationService;
    
    @Before
    public void setUp() throws Exception {
        observationService = new ObservationService();
        userService = new UserService(observationService);
        observationService.setDatabase("jdbc:sqlite:testObservation.db");
        userService.setDatabase("jdbc:sqlite:testUser.db");
        
        userService.createUser("test_user", "Test User", "erinomainensalalause");
    }
    
    @After
    public void tearDown() {
        File file1 = new File("testObservation.db");
        file1.delete();
        File file2 = new File("testUser.db");
        file2.delete();
    }
    
    
    @Test
    public void nonExistingUserCannotLogIn() throws Exception {
        boolean result = userService.login("nonexisting", "password");
        assertFalse(result);
        assertEquals(null, userService.getLoggedUser());
    }    
    
    @Test
    public void existingUserCanLogIn() throws Exception {
        boolean result = userService.login("test_user", "erinomainensalalause");
        assertTrue(result);
        
        User loggedIn = userService.getLoggedUser();
        assertEquals("Test User", loggedIn.getName() );
    }
    
    @Test
    public void loggedInUserCanLogout() throws Exception {
        userService.login("test_user", "erinomainensalalause");
        userService.logout();
        
        assertEquals(null, userService.getLoggedUser());
    }    
    
    @Test
    public void userCreationFailsIfNameNotUnique() throws Exception {
        boolean result = userService.createUser("test_user", "Test User", "erinomainensalalause");
        assertFalse(result);
    }
    
    @Test
    public void succesfullyCreatedUserCanLogIn() throws Exception {
        boolean result = userService.createUser("maijam", "Maija Meikäläinen", "jokulause");
        assertTrue(result);
        
        boolean loginOk = userService.login("maijam", "jokulause");
        assertTrue(loginOk);
        
        User loggedIn = userService.getLoggedUser();
        assertEquals("Maija Meikäläinen", loggedIn.getName() );
    } 
    

    
}
