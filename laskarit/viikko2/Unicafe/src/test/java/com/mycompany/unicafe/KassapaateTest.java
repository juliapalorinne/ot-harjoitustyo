package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate paate;
    Maksukortti kortti;

    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuPaateOlemassa() {
        assertTrue(paate!=null);      
    }
    
    @Test
    public void saldonAlkuarvoOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void edullistenLounaidenAlkuarvoOikein() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaudenLounaidenAlkuarvoOikein() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiToimiiTasarahalla() {
        assertEquals(0, paate.syoEdullisesti(240));
    }
    
    @Test
    public void syoMaukkaastiToimiiTasarahalla() {
        assertEquals(0, paate.syoMaukkaasti(400));
    }
    
    @Test
    public void syoEdullisestiLisaaRahaaKassaan() {
        paate.syoEdullisesti(240);
        paate.syoEdullisesti(1000);
        assertEquals(100480, paate.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiLisaaRahaaKassaan() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(1000);
        assertEquals(100800, paate.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiPalauttaaRahaa() {
        assertEquals(760, paate.syoEdullisesti(1000));
    }
    
    @Test
    public void syoMaukkaastiPalauttaaRahaa() {
        assertEquals(600, paate.syoMaukkaasti(1000));
    }
    
    @Test
    public void syoEdullisestiLisaaMyytyjaLounaita() {
        paate.syoEdullisesti(240);
        paate.syoEdullisesti(1000);
        assertEquals(2, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiLisaaMyytyjaLounaita() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(1000);
        assertEquals(2, paate.maukkaitaLounaitaMyyty());
    }    
    
    @Test
    public void syoEdullisestiPalauttaaRahatJosRahaEiTarpeeksi() {
        assertEquals(200, paate.syoEdullisesti(200));
    }
    
    @Test
    public void syoMaukkaastiPalauttaaRahatJosRahaEiTarpeeksi() {
        assertEquals(200, paate.syoMaukkaasti(200));
    }    
    
        
    @Test
    public void syoEdullisestiEiLisaaRahaaKassaanJosRahatPalautettu() {
        paate.syoEdullisesti(200);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiEiLisaaRahaaKassaanJosRahatPalautettu() {
        paate.syoMaukkaasti(200);
        assertEquals(100000, paate.kassassaRahaa());
    }    
    
    @Test
    public void syoEdullisestiEiLisaaMyytyjaJosRahatPalautettu() {
        paate.syoEdullisesti(200);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiEiLisaaMyytyjaJosRahatPalautettu() {
        paate.syoMaukkaasti(200);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiMaksuToimiiKortilla() {
        assertEquals(true, paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiMaksuToimiiKortilla() {
        assertEquals(true, paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoEdullisestiKortinSummaMuuttuu() {
        paate.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiKortinSummaMuuttuu() {
        paate.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }     
    
        @Test
    public void syoEdullisestiKortillaLisaaMyytyjaLounaita() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaLisaaMyytyjaLounaita() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    } 

    @Test
    public void syoEdullisestiMaksuEiToimiJosKortillaEiRahaa() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(false, paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiMaksuEiToimiJosKortillaEiRahaa() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(false, paate.syoMaukkaasti(kortti));
    }

    @Test
    public void syoEdullisestiKortinSummaEiMuutuJosEiTarpeeksiRahaa() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiKortinSummaEiMuutuJosEiTarpeeksiRahaa() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
    }    
    
    
    @Test
    public void syoEdullisestiMyytyjenMaaraEiMuutuJosKorttimaksuEiOnnistu() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);
        assertEquals(2, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiMyytyjenMaaraEiMuutuJosKorttimaksuEiOnnistu() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttimaksuEiLisaaRahaaKassaan() {
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausLisaaRahaaKortille() {
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(2000, kortti.saldo());
    }
    
    @Test
    public void kortinLatausEiLisaaKortilleJosSummaNegatiivinen() {
        paate.lataaRahaaKortille(kortti, -1000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void kortinLatausLisaaRahaaKassaan() {
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, paate.kassassaRahaa());
    }    
    
    @Test
    public void kortinLatausEiLisaaKassaanJosSummaNegatiivinen() {
        paate.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000, paate.kassassaRahaa());
    }
}
