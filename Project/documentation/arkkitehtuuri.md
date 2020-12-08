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

## Tietokantataulu
![Tietokantataulut](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/kuvat/tietokantataulut.jpg)

