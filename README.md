### BookManagement

Build Status [![Build Status](https://travis-ci.org/hungqtc/BookManagement.svg?branch=update_project)](https://travis-ci.org/hungqtc/BookManagement)

1. Read API's description: [Swagger](https://localhost:8082/swagger-ui.html)

1. Config database in src/main/resources/application.properties: 
 * spring.datasource.username=YOUR_USERNAME
 * spring.datasource.password=YOUR_PASSWORD

1. Build project: mvn clean install

1. Run project: mvn spring-boot:run

1. Use Postman to call API
 * Ex: https://localhost:8082/api/books
 * Info User Login: (user/password/role)
 * hung/123456/ADMIN
 * hoa/123456/USER
