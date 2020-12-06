
package project.dao;

import java.io.File;
import java.io.IOException;
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


public class UserDaoTest { 
    
    UserDatabaseDao userDao;
    File testFolder;
        
    
    @Before
    public void setUp() throws Exception {
        testFolder = new File("testFolder");
        testFolder.mkdir();
        userDao = new UserDatabaseDao("jdbc:sqlite:testFolder/user.db");
        
        User u1 = new User("maija_m", "Maija Meikäläinen", "salalause1");
        User u2 = new User("matti_m", "Matti Meikäläinen", "salalause2");
        userDao.addUser(u1);
        userDao.addUser(u2);
    }
    
    @After
    public void tearDown() {
        new File("testFolder/user.db").delete();
        testFolder.delete();
    }
    
    
    @Test
    public void usersCanBeAddedToDatabaseAndFoundById() throws Exception {
        User u = userDao.findUserById(1);
        assertEquals("maija_m", u.getUsername());
    }
    
    @Test
    public void nonExistingUserCanNotBeFoundById() throws Exception {
        User u = userDao.findUserById(3);
        assertEquals(null, u);
    }
    
    @Test
    public void placesCanBeRemovedFromDatabase() throws Exception {
        userDao.removeUser(2);
        assertEquals(1, userDao.getAllUsers().size());
    }
    
    @Test
    public void placesCanBeModified() throws Exception {
        userDao.modifyUser(2, "", "", "parempisalasana");
        assertEquals("parempisalasana", userDao.findUserById(2).getPassword());
    }
    
    
   
 }
