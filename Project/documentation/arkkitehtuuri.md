# Arkkitehtuuri


## Pakkauskaavio

Ohjelma noudattaa kerrosarkkitehtuuria. Pakkaus project.ui sisältää JavaFX:llä toteutetun käyttöliittymän, project.domain sovelluslogiikan ja project.dao tietojen pysyväistallennuksen. Pakkaus projetc.scenes sisältää käyttöliittymän näkymiä, jotka on selkeyden vuoksi eriytetty omaan pakkaukseensa.


![Pakkauskaavio](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/pakkauskaavio.jpg)

## Ohjelman rakenne

Käyttöliittymä sisältää tällä hetkellä 7 eri näkymää:
- Aloitusnäkymä, josta käyttäjä kirjautuu sisään
- Näkymä uuden käyttäjän luomiseen
- Perusnäkymä, jossa näkyyvät käyttäjän uusimmat havainnot taulukossa ja joka sisältää valikoita ja nappuloita muiden toimintojen toteuttamiseen
- Näkymä uuden havainnon luomiseen
- Näkymä uuden lajin lisäämiseen
- Näkymä uuden paikan lisäämiseen
- Näkymä havaintojen etsimiseen

Kaaviossa on kuvattu ohjelman tämänhetkinen rakenne havaintojen käsittelyn osalta. Paikan ja lajin käsittelyyn tarkoitetut luokat eivät näy kuvassa. Rakennetta tullaan kehittämään uusien ominaisuuksien käyttöönoton yhteydessä.
<br/><br/>

![Ohjelman rakenne](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/rakenne.jpg)

<br/><br/>

## Sovelluslogiikka

Sovellusta käytetään graafisesta käyttöliittymästä, jossa eri toimintoja voi suorittaa painamalla nappeja.

Kun käyttäjä avaa sovelluksen, kysytään käyttäjältä kirjautumistiedot. Kun käyttäjä kirjautuu sisään onnistuneesti, hän näkee perusnäkymässä tähän mennessä tallentamansa havainnot, jotka haetaan tietokannasta.

Kun käyttäjä valitsee painikkeen *Create new observation*, hän pääsee luomaan uuden havainnon. Havainnosta kysytään laji, paikka, yksilöiden määrä, aika ja muut tiedot. Päivämäärä valitaan kalenterista. Paikka ja laji valitaan listalta, joihin haetaan tietokannasta jo sinne tallennetut paikat ja lajit. Kun kaikkiin kohtiin on syötetty jotakin, käyttäjä voi valita painikkeen *Create* ja havainto tallentuu tietokantaan.

Jos käyttäjä haluaa lisätä havainnolleen uuden paikan tai lajin, hän voi klikata painiketta *Add new place* tai *Add new species*. Näistä hän pääsee syöttämään uuden lajin tai paikan tietoja tekstikenttiin. Kun vaaditut kentät on täytetty, käyttäjä voi valita *Add place* tai *Add species*, jolloin uusi laji tai paikka tallentuu tietokantaan. Kun paikka tai laji on lisätty, käyttäjä palaa takaisin uuden havainnon syöttämisnäkymään.

Kun uusi havainto on tallennettu, käyttäjä palaa taulukkonäkymään, jonne myös hänen juuri tallentamansa havainto haetaan tietokannasta.

Jos käyttäjä haluaa hakea havaintoja, hän voi avata hakuikkunan. Käyttäjä voi syöttää ikkunaan lajin nimen, paikan nimen tai molemmat, ja painamalla *Search*-nappia hän saa näkyviin tietokannasta ne havainnot, joiden lajin tai paikan nimessä esiintyvät annetut hakusanat.

## Tietokantataulu
![Tietokantataulut](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/tietokantataulut.jpg)

