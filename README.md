# TriviaClient

## TL;DR
* **Gotova klijentska aplikacija se može preuzeti za Mac, Windowse i kao JAR [ovdje](https://drive.google.com/open?id=1T8gWPx-VExwQIaaZ3CYgZUo1xAIyfBu7) (*Napomena: za pokretanje JAR-a potrebna je Java 1.9+*)**

## Opis
Ovo je drugi dio projekta za Objektno Programiranje 2 i Uvod u XML programiranje.
Sadrži klijentsku aplikaciju Trivia platforme koja komunicira sa serverom. Aplikacija je napravljena u Java FX te koristi JAX-RS client API.

Aplikacija prvi prvom pokretanju automatski pristupa serveru i registrira se kao klijent postavljenom provideru (pod `ClientManager`). Podatke o kategorijama i pitanjima dobiva putem REST API-a.

Aplikacija (ali ne i pitanja!) je internacionalizirana i dostupna na dva jezika, engleskom i hrvatskom.

Serverska aplikacija dostupna je na [GitHubu](https://github.com/Internecivus/TriviaServer).

## Upute
1. Downloadajte i instalirajte [Java JDK 10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
(pazite da možda IDE koji koristite nema bundelanu verziju manju od 1.9).

2. Downloadajte i "instalirajte" [Maven](https://maven.apache.org) (opcionalno možete koristiti IDE koji ima integraciju s Mavenom kao što je IntelliJ).

3. Nativnu aplikaciju i JAR generirate tako da navigirate u direktorij projekta i pokrenete naredbu `mvn package`.