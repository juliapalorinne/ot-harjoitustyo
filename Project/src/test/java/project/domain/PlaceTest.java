
package project.domain;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PlaceTest {
    Place place1;
    Place place2;
    Place place3;
    
        
    @Before
    public void setUp() {
        this.place1 = new Place(1, "Finland", "Helsinki", "Viikki", "shore");
        this.place2 = new Place(1, "Finland", "Helsinki", "Kalasatama", "city");
        this.place3 = new Place(2, "Finland", "Helsinki", "Kalasatama", "city");
    }
    
    
    @Test
    public void equalWhenSameId() {
        assertTrue(place1.equals(place2));
    }
    
    @Test
    public void nonEqualWhenDifferentId() {
        assertFalse(place1.equals(place3));
    } 
    
    @Test
    public void nonEqualWhenDifferentType() {
        Object o = new Object();
        assertFalse(place1.equals(o));
    }     
    
    
    @Test
    public void countrySetterAndGetterWork() {
        place1.setCountry("Sweden");
        assertEquals("Sweden", place1.getCountry());
    }  
    
    @Test
    public void citySetterAndGetterWork() {
        place1.setCity("Hanko");
        assertEquals("Hanko", place1.getCity());
    }  
    
    @Test
    public void spotSetterAndGetterWork() {
        place1.setSpot("Malmi");
        assertEquals("Malmi", place1.getSpot());
    }  
    
    @Test
    public void typeSetterAndGetterWork() {
        place1.setType("forest");
        assertEquals("forest", place1.getType());
    }  
 }
