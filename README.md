# Ohjelmistotekniikka, syksy 2020

## Lintuhavaintosovellus

Sovellus on tarkoitettu lintuhavaintojen tallentamiseen ja vanhojen havaintojen tarkasteluun. Käyttäjä voi tallentaa sovelluksella havaintoja ja liittää niihin paikkoja ja lajeja. Lisäksi havainnoista tallennetaan päivämäärä, kellonaika, yksilöiden lukumäärä ja muut tiedot. Käyttäjä voi hakea havaintoja omista havainnoistaan paikan ja lajin perusteella.

Sovellukseen on pian tulossa toiminnot havaintojen, lajien ja paikkojen muokkaamiseen ja poistamiseen sekä toisten käyttäjien havaintojen tarkastelemiseen.


## Dokumentaatio

[*Työaikakirjanpito*](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/tyoaikakirjanpito.md)

[*Arkkitehtuuri*](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/arkkitehtuuri.md)

[*Vaatimusmäärittely*](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/vaatimusmaarittely.md)

[*Käyttöohje*](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kayttoohje.md)

[*Testaus*](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/testaus.md)


## Releaset

[*Uusin release: 8.12.2020 Save and search observations*](https://github.com/juliapalorinne/ot-harjoitustyo/releases)

## Komentoriviltä suoritettavat komennot

Ohjelmasta voi luoda jar-tiedoston komennolla <code>mvn package</code>.

Testit voi suorittaa komennolla <code>mvn test</code>.

Testikattavuusraportin saa näkyviin komennolla <code>mvn jacoco:report</code>. ja testiraportti tallentuu seuraavaan tiedostoon *target/site/jacoco/index.html*.

Checkstyle-raportin saa näkyviin komennolla <code>mvn jxr:jxr checkstyle:checkstyle</code> ja testiraportti tallentuu tiedostoon */target/site/checkstyle.html*.

Javadocin saa näkyviin komennolla <code>mvn javadoc:javadoc</code> ja generoitu JavaDoc löytyy kansiosta */target/site/apidocs/*.
