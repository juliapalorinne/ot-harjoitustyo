
package project.domain;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SpeciesTest {
    Species species1;
    Species species2;
    Species species3;
    
        
    @Before
    public void setUp() {
        this.species1 = new Species(1, "magpie", "Pica pica", "harakka", "picpic");
        this.species2 = new Species(1, "magpie", "Pica pica", "harakka", "picpic");
        this.species3 = new Species(2, "magpie", "Pica pica", "harakka", "picpic");
    }
    
    
    @Test
    public void equalWhenSameId() {
        assertTrue(species1.equals(species2));
    }
    
    @Test
    public void nonEqualWhenDifferentId() {
        assertFalse(species1.equals(species3));
    } 
    
    @Test
    public void nonEqualWhenDifferentType() {
        Object o = new Object();
        assertFalse(species1.equals(o));
    }     
    
    
    @Test
    public void englishNameSetterAndGetterWork() {
        species1.setEnglishName("crow");
        assertEquals("crow", species1.getEnglishName());
    }
    
    @Test
    public void scientificNameSetterAndGetterWork() {
        species1.setScientificName("Columba livia");
        assertEquals("Columba livia", species1.getScientificName());
    }
    
    @Test
    public void finnishNameSetterAndGetterWork() {
        species1.setFinnishName("pulu");
        assertEquals("pulu", species1.getFinnishName());
    }
    
    @Test
    public void abbreviationSetterAndGetterWork() {
        species1.setAbbreviation("colliv");
        assertEquals("colliv", species1.getAbbreviation());
    }
 }
