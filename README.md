# Deployment guide

## Version of infrastructure:
* Java: 17.0.8
* JDK: 17
* Gradle: 7.5
* Spring Boot: 2.5.2
* Mysql: 8.0.28 - 8.0.34

## Configuration file:
### location: src/main/resources/application.properties

### Database configuration:
#### spring.datasource.primary.url=jdbc:mysql://<db-host>:3306/esoftdb
#### spring.datasource.primary.username=esoftdb
#### spring.datasource.primary.password=esoftdb
#### spring.datasource.primary.driver-class-name=com.mysql.cj.jdbc.Driver

### Redis Configuration
#### spring.redis.host=localhost
####  spring.redis.port=6379

## JWT Configuration
#### app.jwtSecret  => secret key for jwt access token
#### app.jwtExpirationMs => expire time for access token
#### app.jwtRefreshSecret   => secret key for jwt refresh token
#### app.refreshExpirationMs => expire time for refresh token

### Springdoc Configuration (Open API)
#### springdoc.show-login-endpoint=true
#### springdoc.swagger-ui.persistAuthorization=true

## Run application:
### From root directory of this source code, run command:
### ./gradlew bootRun
### Service listen on 8080, able to access over URL:
#### http://localhost:8080/

## Register with role ROLE_ADMIN to create admin account can use management function

## Build:
#### ./gradlew clean build
