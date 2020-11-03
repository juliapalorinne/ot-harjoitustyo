
package project.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ObservationtTest {
    
    @Test
    public void equalWhenSameId() {
        Observation obs1 = new Observation(1, null, null, null, null, null, null);
        Observation obs2 = new Observation(1, null, null, null, null, null, null);
        assertTrue(obs1.equals(obs2));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        Observation obs1 = new Observation(1, null, null, null, null, null, null);
        Observation obs2 = new Observation(11, null, null, null, null, null, null);
        assertFalse(obs1.equals(obs2));
    }   
    
    @Test
    public void nonEqualWhenDifferentType() {
        Observation obs1 = new Observation(1, null, null, null, null, null, null);
        Object o = new Object();
        assertFalse(obs1.equals(o));
    }      

}
