# Arkkitehtuuri


## Pakkauskaavio

Ohjelma noudattaa kerrosarkkitehtuuria. Pakkaus project.ui sisältää JavaFX:llä toteutetun käyttöliittymän, project.domain sovelluslogiikan ja project.dao tietojen pysyväistallennuksen. Pakkaus projetc.scenes sisältää käyttöliittymän näkymiä, jotka on selkeyden vuoksi eriytetty omaan pakkaukseensa.


![Pakkauskaavio](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/pakkauskaavio.jpg)

## Ohjelman rakenne

Käyttöliittymä sisältää 13 eri näkymää:
- Aloitusnäkymä, josta käyttäjä kirjautuu sisään
- Näkymä uuden käyttäjän luomiseen
- Perusnäkymä, jossa näkyyvät käyttäjän havainnot taulukossa ja joka sisältää valikot muiden toimintojen toteuttamiseen
- Näkymä uuden havainnon luomiseen
- Näkymä uuden lajin lisäämiseen
- Näkymä uuden paikan lisäämiseen
- Näkymä havaintojen etsimiseen
- Näkymä yhden havainnon avaamiseen ja muokkaamiseen
- Näkymä yhden lajin avaamiseen ja muokkaamiseen
- Näkymä yhden paikan avaamiseen ja muokkaamiseen
- Näkymä kaikkien käyttäjien julkisten havaintojen hakemiseen ja tarkasteluun

Kaaviossa on kuvattu ohjelman rakenne muiden osien kuin erilaisten näkymien osalta.
<br/><br/>

![Ohjelman rakenne](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/rakenne.jpg)

<br/><br/>

## Sovelluslogiikka

Sovellusta käytetään graafisesta käyttöliittymästä, jossa eri toimintoja voi suorittaa painamalla nappeja.

Kun käyttäjä avaa sovelluksen, kysytään käyttäjältä kirjautumistiedot. Kun käyttäjä kirjautuu sisään onnistuneesti, hän näkee perusnäkymässä tähän mennessä tallentamansa havainnot, jotka haetaan tietokannasta. Käyttäjä voi avata havainnon napauttamalla sitä kahdesti. Tällöin aukeaa uusi ikkuna, jossa käyttäjä näkee kaikki havainnosta tallennetut tiedot ja voi muokata niitä. Muutokset tallentuvat tietokantaan painamalla *Modify observation* ja perusnäkymään voi palata takaisin muokkaamatta havaintoa painamalla *Return*.

Kun käyttäjä valitsee painikkeen *Create new observation*, hän pääsee luomaan uuden havainnon. Havainnosta kysytään laji, paikka, yksilöiden määrä, aika ja muut tiedot. Päivämäärä valitaan kalenterista. Paikka ja laji valitaan listalta, joihin haetaan tietokannasta jo sinne tallennetut paikat ja lajit. Kun kaikkiin kohtiin on syötetty hyväksyttävä syöte, käyttäjä voi valita painikkeen *Create* ja havainto tallentuu tietokantaan.

Jos käyttäjä haluaa lisätä havainnolleen uuden paikan tai lajin, hän voi klikata painiketta *Add new place* tai *Add new species*. Näistä hän pääsee syöttämään uuden lajin tai paikan tiedot tekstikenttiin. Kun vaaditut kentät on täytetty, käyttäjä voi valita *Add place* tai *Add species*, jolloin uusi laji tai paikka tallentuu tietokantaan. Kun paikka tai laji on lisätty, käyttäjä palaa takaisin uuden havainnon syöttämisnäkymään.

Kun uusi havainto on tallennettu, käyttäjä palaa taulukkonäkymään, jonne myös hänen juuri tallentamansa havainto haetaan tietokannasta.

Jos käyttäjä haluaa hakea havaintoja, hän voi avata hakuikkunan. Käyttäjä valitsee, haluaako hän hakea lajin, paikan, päivämäärän, kellonajan vai muiden tietojen perusteella, ja taulukossa näkyvät havainnot rajautuvat automaattisesti sitä mukaa, kun käyttäjä syöttää hakusanaa hakukenttään. Käyttäjä voi jälleen avata havainnon tarkasteltavaksi ja muokattavaksi napauttamalla sitä kahdesti.

Käyttäjä voi tehdä hakuja myös muiden käyttäjien julkiseksi määrittämistä havainnoista. Napauttamalla painiketta *Show observations by all users* käyttäjä pääsee edellistä vastaavaan hakunäkymään, jossa aiempien lisäksi voi tehdä hakuja myös käyttäjän nimen perusteella. Tässä näkymässä ei kuitenkaan voi avata eikä muokata havaintoja.

Halutessaan lopettaa sovelluksen käytön käyttäjä valitsee painikkeen *Logout*, josta hän pääsee takaisin kirjautumisnäkymään.



## Tietokantataulu
![Tietokantataulut](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/tietokantataulut.jpg)

