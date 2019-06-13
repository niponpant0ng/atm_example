# Libraries and Framework
**spring boot starter web** use for build a restful webservice that running on embedded tomcat server

**spring boot starter data jpa** use for persist data in SQL store

**spring boot starter test** use for testing webservice including a service that running inside

**spring boot thymeleaf** use for render static page in resources/templates

**h2** use for store notes data into memory using **spring boot starter data jpa**

**lombok** use for reduce boilerplate code while coding such as setter/getter

**junit-addons** use for assert list data type in test case

**spring-fox-swagger** use for create swagger doc

# Endpoint Form of ATM Application
GET /form/dispenseForm = display dispense form

GET /form/initNotes = display init notes form

# Endpoint Service of ATM Application
GET /notes = list notes in atm application

POST /notes = insert or update counting of notes in atm application

POST /dispense/{amount} = dispense amount in atm application

or using this **<IP>:<PORT>/swagger-ui.html** endpoint to access swagger doc for see a detail of endpoint and play with it

# How to Running Service

##### mvn available in machine
1. go inside project folder
2. create package with dependency using mvn package
3. run service using mvn spring-boot run --server.port=**<port(default is 8081)>**

##### mvn not available in machine
1. go inside project folder
2. create package with dependency using ./mvnw package
3. run service using ./mvnw spring-boot run --server.port=**<port(default is 8081)>**