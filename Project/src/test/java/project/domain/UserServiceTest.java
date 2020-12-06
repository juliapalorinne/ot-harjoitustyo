package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
        ObservationService observationService = new ObservationService();
        userDao = new FakeUserDatabaseDao();
        userService = new UserService(observationService);
        userService.setDatabase(userDao);
        userService.createUser("liisa", "Liisa", "salasana1");
        userService.createUser("ville", "Ville Vallaton", "salasana2");
        userService.createUser("keijo_k", "Keijo Kokeilija", "jokumuusalasana");
        userService.createUser("matilda_m", "Matilda Mietiskelijä", "matildansalasana");
    }
    
    @Test
    public void userCanLogIn() throws Exception {
        userService.login("matilda_m", "matildansalasana");
        assertEquals(userService.getUserById(4), userService.getLoggedUser());
    }

    @Test
    public void nonexistingUserCannotLogIn() throws Exception {
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
   
    
}
