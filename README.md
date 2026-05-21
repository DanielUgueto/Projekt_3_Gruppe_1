Bilabonnement 2026
Internt administrationssystem for Bilabonnement A/S.
En webapplikation der erstatter virksomhedens Excel baseret arbejdsgange.

Systemet har tre interne brugerroller:
- Dataregistrering: Opretter og vedligeholder lejeaftaler, kunder, medarbejdere og biler
- Skade & udbedring: registrerer skader, fejl og mangler efter endt lejeperiode.
- Forretningsudviklere: kan tilgå rapportering og KPI'er

Projektet er udviklet som eksamensprojekt på 2. semester af datamatikeruddannelsen, EK.


Arkitektur
Appen følger MVC arkitektur med streng lagadskillelse
Controller -> Service -> Repository -> MySQL

Controller modtager HTTP requests.
Service står for forretningslogik og validering.
Repository står for al kommunikation med SQL via jdbcTemplate.


Links
- Live deployment (Azure): http://bilabonnementgruppe1projekt3-frgxd3aravfqb7ef.swedencentral-01.azurewebsites.net

Demobrugere
Til afprøvning af systemet er der oprettet en demobruger pr. rolle.
Rolle                Brugernavn          Password
Dataregistrering      demo1              demo
Skade & udbedring     demo2              demo
Forretningsudvikler   demo3              demo


Database
Produktionsdatabasen er hostet via Azure VM med MySQL.
DDL(DatabaseScript.sql) og DML(DummyData.sql) scripts ligger i /src/main/resources og kan køres mod en tom MySQL database for at genskabe DB layout og demodata.


Software forudsætninger
For at bygge og køre applikationen lokalt kræves:
- Java JDK  V21
- Maven 3.9 eller nyere
- MySQL 8.0 eller nyere
- Git
  

Installation og kørsel
- Klon repository https://github.com/DanielUgueto/Projekt_3_Gruppe_1
- Opret en tom MySQL database kaldet bilabonnement  CREATE DATABASE bilabonnement;
- Kør database scripts i den nye database for at oprette tabeller og indlæse demodata. 
Filerne ligger i src/main/resources
    - DDL: src/main/resources/DatabaseScript.sql
    - DML: src/main/resources/DummyData.sql
- Sæt miljøvariabler. Databaseforbindelsen bruger miljøvariabler så ingen credential ligger i kildekoden.
  I src/main/resources/application.properties skal følgende stå:
  - JDBC_DATABASE_URL  eksempel: jdbc:mysql://localhost:3306/bilabonnement
  - JDBC_USERNAME      eksempel: root
  - JDBC_PASSWORD      eksempel: dit password
 I IntelliJ IDEA sættes disse under Run/Debug Configurations -> Enviroment variables.

- Byg og kør applikationen.
- Åben http://localhost:8080 i en brower og log ind med en demobruger


Test
Projektet indeholder unit og integrations test skrevet.
Servie laget testes isoleret med mockede repositories, mens repository laget testes mod den rigtige database(Den der laves lokalt).

Deployment (CI/CD)
Deployment er automatiseret via GitHub Actions.


Projektstruktur

src/
├── main/
│   ├── java/dk/ek/bilabonnement2026/
│   │   ├── controller/   HTTP-endpoints og viewvalg
│   │   ├── service/      Forretningslogik og validering
│   │   ├── repository/   Dataadgang (JdbcTemplate)
│   │   └── model/        Domæneklasser (Person, Customer, Car, ...)
│   └── resources/
│       ├── templates/    Thymeleaf-views
│       ├── static/       CSS, billeder
│       ├── DatabaseScript.sql
│       ├── DummyData.sql
│       └── application.properties
└── test/                 JUnit- og Mockito-tests
        


Holdinformation
Gruppe:   1
Hold:     DATA-GBG-E25C 2.semester
Projekt:  3
Medlemmer: Rune, Nico, Daniel
