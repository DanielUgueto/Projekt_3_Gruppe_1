Bilabonnement 2026
Internt administrationssystem for Bilabonnement A/S.
En webapplikation der erstatter virksomhedens Excel baseret arbejdsgange.

Systemet har tre interne brugerroller:
- Dataregistrering: Opretter og vedligeholder lejeaftaler, kunder, medarbejdere og biler
- Skade & udbedring: registrerer skader, fejl og mangler efter endt lejeperiode.
- Forretningsudviklere: kan tilgå rapportering og KPI'er

Projektet er udviklet som eksamensprojekt på 2. semester af datamatikeruddannelsen, EK.


Holdinformation
- Rune
- Daniel
- Nico

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
- Opret en tom MySQL database kaldet bilabonnement
- 
