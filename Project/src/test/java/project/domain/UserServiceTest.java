package project.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import project.dao.FakeUserDatabaseDao;


public class UserServiceTest {

    private UserService userService;
    private FakeUserDatabaseDao userDao;
    
    
    @Before
    public void setUp() throws Exception {
        StoreableObservationService observationService = new StoreableObservationService();
        userDao = new FakeUserDatabaseDao();
        userService = new UserService(observationService);
        userService.setDatabase(userDao);
        userService.createUser("liisa", "Liisa", "salasana1");
        userService.createUser("ville", "Ville Vallaton", "salasana2");
        userService.createUser("keijo_k", "Keijo Kokeilija", "jokumuusalasana");
        userService.createUser("matilda_m", "Matilda Mietiskelijä", "matildansalasana");
    }
    
    @Test
    public void existingUserCanLogInAndOut() throws Exception {
        userService.login("matilda_m", "matildansalasana");
        assertEquals(userService.getUserById(4), userService.getLoggedUser());
        userService.logout();
        assertEquals(null, userService.getLoggedUser());
    }
    
    @Test
    public void existingUserCannotLogInWithWrongPassword() throws Exception {
        userService.login("matilda_m", "jokumuusalasana");
        assertEquals(null, userService.getLoggedUser());
    }

    @Test
    public void nonExistingUserCannotLogIn() throws Exception {
        userService.login("taina", "tainansalasana");
        assertEquals(null, userService.getLoggedUser());
    }

    @Test
    public void getAllReturnsAllUsers() throws Exception {
        assertEquals(4, userService.getAllUsers().size());
    }
    
    @Test
    public void removeUserRemovesUser() throws Exception {
        userService.removeUser(1);
        assertEquals(3, userService.getAllUsers().size());
        assertEquals(null, userService.getUserById(1));
    }
    
    @Test
    public void userCanBeFoundByUsername() throws Exception {
        User u = userService.getUserByName("keijo_k", "username");
        assertEquals("Keijo Kokeilija", u.getName());
        
    }
   
    
}
