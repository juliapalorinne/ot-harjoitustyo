# Testaus

Testit voi suorittaa komennolla <code>mvn test</code>.

Testikattavuusraportin saa näkyviin komennolla <code>mvn jacoco:report</code>. ja testiraportti tallentuu seuraavaan tiedostoon *target/site/jacoco/index.html*.

Checkstyle-raportin saa näkyviin komennolla <code>mvn jxr:jxr checkstyle:checkstyle</code> ja testiraportti tallentuu tiedostoon */target/site/checkstyle.html*.

Javadocin saa näkyviin komennolla <code>mvn javadoc:javadoc</code> ja generoitu JavaDoc löytyy kansiosta */target/site/apidocs/*.
