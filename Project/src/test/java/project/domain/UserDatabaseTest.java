
package project.domain;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import project.dao.TestUserDatabaseDao;


public class UserDatabaseTest {
    TestUserDatabaseDao userDao;
    
        
    @Before
    public void setUp() throws Exception {
        this.userDao = new TestUserDatabaseDao("jdbc:sqlite:testUser.db");
        User u1 = new User("johanna", "Johanna", "salalause");
        userDao.addUser(u1);
    }
    
    @Test
    public void userCanBeSavedToDatabase() throws Exception {
        User u = new User("pekka", "Pekka", "salalause2");
        userDao.addUser(u);
        User u2 = userDao.findUserByName("pekka", "username");
        assertEquals(u2.getName(), "Pekka");
    }
    
    @Test
    public void userIsFoundByUsername() throws Exception {
        User u = userDao.findUserByName("johanna", "username");
        assertEquals(u.getName(), "Johanna");
    }
    
    @Test
    public void userIsFoundByName() throws Exception {
        User u = userDao.findUserByName("Johanna", "username");
        assertEquals(u.getUsername(), "johanna");
    }
    
    
    
}
