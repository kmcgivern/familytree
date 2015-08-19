springhibernate
===============

sample spring hibernate project for reference

Uses Spring Boot so to run do the following

at springhibernate directory level
mvn clean install

cd rest
mvn spring-boot:run

POST the following JSON to http://localhost:9000/people

{
  "id" : 0,
  "firstName" : "Bob",
  "middleName" : "Chaz",
  "lastName" : "Davids",
  "birthDate" : 1405676660069,
  "addresses" : [ {
    "id" : 0,
    "firstLine" : "1 New Street",
    "secondLine" : "",
    "town" : "Belfast",
    "postCode" : "BT1 1AB",
    "people" : [ ]
  } ]
}

then navigate to

http://localhost:9000/people?Id=1
http://localhost:9000/addresses?Id=1
