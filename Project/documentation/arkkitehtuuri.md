# Arkkitehtuuri

## Tietokantataulu
![][tietokantataulut.jpg]


### Tietokantataulun koodi

[User]1-*[Observation]
[Observation]*-1[Species]
[Observation]*-1[Place]

[User|username: string; name: string; password: string||login(user, password)]
[Observation|id: integer; species: Species; place: Place; date: Date; time: Time; info: string; user: User]
[Species|abbreviation: string; englishName: string; latinName: string]
[Place|id: integer; name: string; coordinate: string]

