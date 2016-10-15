# totemSpringProject
Sends each row of data from CSV from client to the server.
Server then records each data in Cassandra and at the same time, stores average of the data in 15 minute intervals to MySQL.
This project uses spring boot 1.4.1 and builds with maven.

## How to Run
Before running, the server needs to have :
  Cassandra 2.x
  ,MySQL
  Oracle Java 1.8 (Had some problem with OpenJDK)

### Client
Before starting the client, user needs to provide .csv file in the /totemClient/src/main/resources file.
User also needs to change the uri in Application.java file to the ip of the server and also the name of the csv file.
To run the client, cd into client directory and run :
```
mvn spring-boot:run
```
The client will send each row to the server until the end of file.

### Server
Before compiling, edit cassandra.properties for cassandra database connection and application.properties for mysql database connection.
Run the queries in the tableQueries file to set up table in each database.
c.cql for cassandra and m.sql for mysql.

To run the server, cd into server directory and run :
```
mvn install
```
Jar file named totemProject-0.1.jar will appear in the target directory.
Transfer the jar file to the server and run the jar.

Detailed exlanation of the project in provided in the wiki.
