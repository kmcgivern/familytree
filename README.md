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
  "firstName" : "Bob",
  "middleName" : "Chaz",
  "lastName" : "Davids",
  "birthDate" : {
    "day":2,
    "month": 2,
    "year":1767
  }
}

then navigate to

http://localhost:9000/people?Id=1
