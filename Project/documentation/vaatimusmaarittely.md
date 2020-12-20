# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on tarkoitettu lintuhavaintojen kirjaamiseen. Sovellus on suunniteltu lintuharrastajille, jotka haluavat pitää kirjaa havaitsemistaan linnuista.
<br/>


## Käyttäjät
Sovelluksessa on aluksi vain yksi käyttäjärooli, **normaali käyttäjä**. Sovellukseen voidaan lisätä myöhemmin esimerkiksi **yhdistyskäyttäjä** tai **hallinnoija**, joka hallinnoi tietyn lintuyhdistyksen alueen havaintoja. Normaali käyttäjä voi lisätä havaintoja, muokata omia havaintojaan, nähdä toisten käyttäjien julkiset havainnot ja lisätä uusia havaintopaikkoja ja lajeja. Alkuvaiheessa normaali käyttäjä voi myös muokata lajeja ja paikkoja, mutta tästä saattaa myöhemmin tulla vain yhdistyskäyttäjien tai hallinnoijien käytössä oleva ominaisuus.

<br/>


## Toiminnallisuudet

- Käyttäjä voi luoda palveluun tunnukset ja kirjautua niillä sisään.
- Käyttäjä kirjaa palveluun **havaintoja**.
- Havainnoista tallennetaan seuraavat tiedot:
    - lintulaji
    - yksilöiden lukumäärä
    - havaintopaikka
    - havaintoaika
    - lisätiedot (esim. muuttava/paikallinen, laulava/muuten ääntelevä/nähty)
    - havaitsija
    - tallennusaika
- Käyttäjä voi etsiä kirjaamiaan havaintoja lajin, paikan, havaintoajan ja muiden tietojen perusteella.
- Käyttäjä voi tarkastella ja muokata kirjaamiaan havaintoja.
- Käyttäjä voi määritellä havainnot salaisiksi tai julkisiksi.
- Käyttäjä voi tarkastella toisten käyttäjien kirjaamia julkisia havaintoja.
- Käyttäjä voi etsiä **lintulajeja** tai **havaintopaikkoja** nimen perusteella ja lisätä uusia, jos niitä ei vielä löydy tietokannasta.


## Jatkokehitys

- Käyttäjä näkee tilastoja kirjaamistaan havainnoista, esim.
    - kuluvana vuonna havaittujen lajien määrä
    - tietyssä paikassa havaittujen lajien määrä
    - tiettynä päivänä havaittujen lajien määrä
    - kirjattujen havaintojen määrä laji- ja paikkakohtaisesti.
- Käyttäjä voi tarkastella omia havaintojaan kartalla.
- Käyttäjä voi etsiä omia tai toisten käyttäjien havaintoja esim. lajin perusteella ja nähdä löydetyt havainnot kartalla.
- Käyttäjä voi klikata kartalla näkyvää havaintoa ja saada sen tiedot näkyviin.
- Käyttäjä voi tarkastella tilastoja myös muiden käyttäjien havainnoista.
