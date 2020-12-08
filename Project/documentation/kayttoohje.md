# Käyttöohje

Tämä on sovellus lintuhavaintojen tallentamiseen.

## Sovelluksen käynnistäminen

Sovellus käynnistetään suorittamalla jar-tiedosto birdingapp.jar.


## Aloitus

Käyttö aloitetaan luomalla käyttäjätunnus, jolla palveluun kirjaudutaan.
Tunnuksen luomiseen tarvitaan nimi, käyttäjätunnus ja salasana.
Kun käyttäjätunnus on luotu, pääsee käyttäjä kirjautumaan palveluun.


## Havaintojen tarkastelu

Kun käyttäjä kirjautuu palveluun, hän näkee taulukkonäkymän omista tallennetuista
havainnoistaan.

Käyttäjä voi avata sivun alalaidasta hakuikkunan, jossa käyttäjä voi hakea havaintoja
esimerkiksi paikan, päivämäärän tai lajin perusteella. Käyttäjä voi valita, haluaako
hän tarkastella vain omiaan vai myös muiden käyttäjien havaintoja.


## Havaintojen tallennus

Tallentaminen tapahtuu lomakkeella, johon käyttäjä syöttää havainnoista
lajin, yksilöiden määrän, paikan, päivämäärän, kellonajan ja havainnon muut tiedot.
Paikka ja laji valitaan valikosta klikkaamalla. Paikkaa ja lajia voi myös hakea.
Jos oikeaa paikkaa tai lajia ei vielä löydy listalta, voi käyttäjä lisätä ne
hakemistoon ja sen jälkeen jatkaa havainnon tallentamista.

Uutta paikkaa lisätessä paikasta annetaan maa, kaupunki, tarkan paikan nimi ja paikan
tyyppi (esimerkiksi *lintutorni*, *kosteikko* tai *merenlahti*). Uudesta lajista
lisätään englanninkielinen, suomenkielinen ja tieteellinen nimi sekä 3+3-lyhenne
(tieteellisen nimen molemmista osista ensimmäiset kolme kirjainta).

Kun käyttäjä tallentaa havainnon, hän valitsee onko se salainen vai julkinen. Jos 
havainto tallennetaan julkisena, myös muut käyttäjät näkevät sen hakuja tehdessään.
Jos havainto tallennetaan salaisena, vain käyttäjä itse näkee havainnon.


## Havaintojen muokkaaminen ja poistaminen

Käyttäjä voi avata havainnon omaan ikkunaansa valitsemalla sen taulukosta ja painamalla *Avaa*-nappia.
Avatessaan havainnon käyttäjä saa näkyviin valinnat *Muokkaa* ja *Poista*.

## Lopetus

Halutessaan lopettaa sovelluksen käytön, käyttäjä pääsee takaisin perusnäkymään painamalla tarpeeksi monta kertaa *Return*-nappia. Perusnäkymän ylälaidassa on painike *Log out*, josta käyttäjä voi kirjautua ulos ja lopettaa käytön.
