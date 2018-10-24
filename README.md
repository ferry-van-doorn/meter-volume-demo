# Meter Volume Demo 

This is a demo application which stores profile information in a database 
and validate incoming metering information against these profiles.

## Running the application

The application is a Java (8) application which uses Gradle, Spring boot and 
a PostgreSQL database to operate. Before the application can be built a 
database must be present and the database credentials must be configured in the application.

To build the application execute:

```console
> ./gradlew build
```

To run the application execute:

```console
> ./gradlew bootRun
```

## Configuration

All configuration can be done in the application.properties.

Database configuration options: 
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/metervolumedemo
spring.datasource.username=demouser
spring.datasource.password=demopassword
```

Metervolumes validation configuration:
```properties
volume.tolerance:0.25
```

Directory which is monitored for uploaded meter reading CSV files:
```properties
meterreading.csv.path:/tmp
```

## How to use the application

Upload a profile:
```console
curl -XPOST -H "Content-Type: application/json" -d @./src/main/resources/examples/profile.json http://localhost:8080/profile
```

When the profile is present meter readings with this profile-id can be uploaded:
```console
curl -XPOST -H "Content-Type: application/json" -d @./src/main/resources/examples/meterreading.json http://localhost:8080/meterreading
```

Fetch all profiles
```console
curl -XGET -H "Content-Type: application/json" http://localhost:8080/profile/all
```

Fetch a specific profile
```console
curl -XGET -H "Content-Type: application/json" http://localhost:8080/profile?id=<id>
```

Fetch all meter readings
```console
curl -XGET -H "Content-Type: application/json" http://localhost:8080/meterreading/all
```

Fetch a specific meter reading
```console
curl -XGET -H "Content-Type: application/json" http://localhost:8080/meterreading?id=<id>
```

To upload meter readings via a CSV-file copy the file to the CSV directory configured with the property meterreading.csv.path. 
Files put in this directory will automatically be picked up and processed.
```console
cp ./src/main/resources/examples/meterreading.csv /tmp
```

## Todo
* The processed meter reading CSV file should be deleted.
* The error reporting of the CSV file processing should be exported to a external file in the same path as the CSV.
* Add feature to read the old CSV-file with profile information.
* Add validation to check uniqueness constraints. Currently a profile with a similar name 
or meter readings for the year which are already upload is only checked with a database constraint.
Currently this results in a HTTP 500 error with an unclear error message. 

