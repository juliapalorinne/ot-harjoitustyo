# Käyttöohje

Tämä on sovellus lintuhavaintojen tallentamiseen.

## Sovelluksen käynnistäminen

Sovellus käynnistetään suorittamalla jar-tiedosto birdingapp.jar.


## Aloitus

Käyttö aloitetaan luomalla käyttäjätunnus, jolla palveluun kirjaudutaan.
Tunnuksen luomiseen tarvitaan nimi, käyttäjätunnus ja salasana. Käyttäjätunnuksen tulee olla uniikki eli se ei saa olla käytössä vielä kellään muulla käyttäjällä. Lisäksi käyttäjätunnuksen ja nimen pituuden tulee olla 2 ja 50 merkin välillä ja salasanan pituuden 8 ja 30 merkin välillä. Kun käyttäjätunnus on luotu, pääsee käyttäjä kirjautumaan palveluun painamalla *Login*.

## Havaintojen tarkastelu ja hakeminen

Kun käyttäjä kirjautuu palveluun, hän näkee taulukkonäkymän omista tallennetuista havainnoistaan.

Käyttäjä voi avata sivun alalaidasta hakuikkunan, jossa käyttäjä voi hakea havaintoja lajin, paikan, päivämäärän, kellonajan tai muiden tietojen perusteella. Taulukossa näkyvät havainnot rajautuvat automaattisesti sitä mukaa, kun käyttäjä syöttää hakusanaa hakukenttään. Käyttäjä voi jälleen avata havainnon tarkasteltavaksi ja muokattavaksi napauttamalla sitä kahdesti.

Käyttäjä voi tarkastella samaan tapaan myös muiden käyttäjien havaintoja valitsemalla *Show observations by all users*, jolloin hakua voi rajata edellisten lisäksi myös käyttäjän nimen perusteella. Tässä näkymässä havaintoja ei kuitenkaan saa avattua ja muokattua.

## Havaintojen tallennus

Kun käyttäjä valitsee painikkeen *Add new observation*, hän pääsee luomaan uuden havainnon. Tallentaminen tapahtuu lomakkeella, johon käyttäjä syöttää havainnoista lajin, yksilöiden määrän, paikan, päivämäärän, kellonajan ja havainnon muut tiedot. Paikka ja laji valitaan valikosta klikkaamalla. Paikkaa ja lajia voi myös hakea. Jos oikeaa paikkaa tai lajia ei vielä löydy listalta, voi käyttäjä lisätä ne hakemistoon ja sen jälkeen jatkaa havainnon tallentamista. Kun kaikki tiedot on syötetty, käyttäjä voi valita painikkeen *Add new observation* ja havainto tallentuu.

Jos käyttäjä haluaa lisätä havainnolleen uuden paikan tai lajin, hän voi klikata painiketta *Add new place* tai *Add new species*. Näistä hän pääsee syöttämään uuden lajin tai paikan tiedot. Kun vaaditut kentät on täytetty, käyttäjä voi valita *Add place* tai *Add species* ja uusi paikka tai laji tallentuu, ja käyttäjä pääsee jatkamaan uuden havainnon tallentamista.

Uutta paikkaa lisätessä paikasta annetaan maa, kaupunki, tarkan paikan nimi ja paikan tyyppi (esimerkiksi *lintutorni*, *kosteikko* tai *merenlahti*). Uudesta lajista
lisätään englanninkielinen, suomenkielinen ja tieteellinen nimi sekä 3+3-lyhenne (tieteellisen nimen molemmista osista ensimmäiset kolme kirjainta).

Kun käyttäjä tallentaa havainnon, hän valitsee onko se salainen vai julkinen. Jos havainto tallennetaan julkisena, myös muut käyttäjät näkevät sen hakuja tehdessään.
Jos havainto tallennetaan salaisena, vain käyttäjä itse näkee havainnon.


## Havaintojen muokkaaminen

Käyttäjä voi avata havainnon omaan ikkunaansa napauttamalla sitä kahdesti. Käyttäjälle aukeaa näkymä havainnon nyt tallennetuista tiedoista sekä muokkausnäkymä, jonka kentissä ovat automaattisesti havainnon nykyiset tiedot. Laji ja paikka eivät kuitenkaan ole automaattisesti valittuna listalta, vaan niiden nimet ovat valmiiksi hakukentissä, ja käyttäjän tulee vielä itse klikata listalla näkyvää lajia ja paikkaa ennen havainnon tallentamista. Käyttäjä voi muokata havaintoa syöttämällä johonkin havainnon kentistä aiemmasta poikkeavia tietoja ja painamalla *Modify observation*. Jos käyttäjä ei halua muokata havaintoa, hän voi palata takaisin aiempaan näkymään painamalla *Return*.

Myös lajia ja paikkaa voi muokata klikkaamalla *Show species list* tai *Show place list* ja valitsemalla muokattavan lajin tai paikan listalta.

## Lopetus

Halutessaan lopettaa sovelluksen käytön käyttäjä pääsee takaisin perusnäkymään painamalla tarpeeksi monta kertaa *Return*-nappia. Perusnäkymän ylälaidassa on painike *Logout*, josta käyttäjä voi kirjautua ulos ja lopettaa sovelluksen käytön.
