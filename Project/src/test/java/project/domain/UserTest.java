
package project.domain;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserTest {
    User user;
    
        
    @Before
    public void setUp() {
        this.user = new User("anna1", "Anna", "salasana1");
    }
    
    
    @Test
    public void equalWhenSameUsername() {
        User u1 = new User("user", "Matti", "salasana");
        User u2 = new User("user", "Matti", "jotainmuuta");
        assertTrue(u1.equals(u2));
    }
    
    @Test
    public void nonEqualWhenDifferentUsername() {
        User u1 = new User("user", "Matti", "salasana");
        User u2 = new User("kayttaja", "Liisa", "jotainmuuta");
        assertFalse(u1.equals(u2));
    } 
    
    @Test
    public void nonEqualWhenDifferentType() {
        User u1 = new User("user", "Matti", "salasana");
        Object o = new Object();
        assertFalse(u1.equals(o));
    }     
    
    
    @Test
    public void usernameSetterAndGetterWork() {
        user.setUsername("johanna");
        assertEquals("johanna", user.getUsername());
    }    

    @Test
    public void nameSetterAndGetterWork() {
        user.setName("Johanna");
        assertEquals("Johanna", user.getName());
    }    


    @Test
    public void passwordSetterAndGetterWork() {
        user.setPassword("Johannaspassword");
        assertEquals("Johannaspassword", user.getPassword());
    }    }
