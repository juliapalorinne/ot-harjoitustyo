
package project.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserServiceTest {

    TestObservationDao obsDao;
    TestUserDao userDao;
    UserService service;
    
    @Before
    public void setUp() {
        userDao = new TestUserDao();
        service = new UserService(obsDao, userDao);     
    }
    
    @Test
    public void nonExistingUserCanLogIn() {
        boolean result = service.login("nonexisting");
        assertFalse(result);
        
        assertEquals(null, service.getLoggedUser());
    }    
    
    @Test
    public void existingUserCanLogIn() {
        boolean result = service.login("test_user");
        assertTrue(result);
        
        User loggedIn = service.getLoggedUser();
        assertEquals("Test User", loggedIn.getName() );
    }
    
    @Test
    public void loggedInUserCanLogout() {
        service.login("test_user");
        service.logout();
        
        assertEquals(null, service.getLoggedUser());
    }    
    
    @Test
    public void userCreationFailsIfNameNotUnique() throws Exception {
        boolean result = service.createUser("test_user", "Test User", "erinomainensalalause");
        assertFalse(result);
    }
    
    @Test
    public void succesfullyCreatedUserCanLogIn() throws Exception {
        boolean result = service.createUser("maijam", "Maija Meikäläinen", "jokulause");
        assertTrue(result);
        
        boolean loginOk = service.login("maijam");
        assertTrue(loginOk);
        
        User loggedIn = service.getLoggedUser();
        assertEquals("Maija Meikäläinen", loggedIn.getName() );
    } 
    

    
}
