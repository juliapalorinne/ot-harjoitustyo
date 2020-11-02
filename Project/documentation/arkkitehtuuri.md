# Arkkitehtuuri

## Tietokantataulu
![Tietokantataulut](https://github.com/juliapalorinne/ot-harjoitustyo/blob/main/Project/documentation/tietokantataulut.jpg)


### Tietokantataulun koodi

[User]1-*[Observation]

[Observation]*-1[Species]

[Observation]*-1[Place]


[User|username: string; name: string; password: string||login(user, password)]
[Observation|id: integer; species: Species; place: Place; date: Date; time: Time; info: string; user: User]
[Species|abbreviation: string; englishName: string; latinName: string]
[Place|id: integer; name: string; coordinate: string]

