package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void alkuarvoOnOikein() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(1000);
        assertEquals(2000, kortti.saldo());
    }
    
    @Test
    public void kortiltaVoiOttaaRahaa() {
        kortti.otaRahaa(500);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void kortiltaEiVoiOttaaLiikaaRahaa() {
        kortti.otaRahaa(2000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosTarpeeksiRahaa() {
        assertEquals(true, kortti.otaRahaa(500));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosEiTarpeeksiRahaa() {
        assertEquals(false, kortti.otaRahaa(2000));
    }
    
    @Test
    public void toStringToimii() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
}
