# Spring Boot Application

This is a Spring Boot application built with Java 17. The application leverages several powerful libraries and frameworks to provide a robust, scalable, and secure RESTful API.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Building and Running](#building-and-running)
- [Testing](#testing)
- [API Documentation](#api-documentation)
- [License](#license)

## Features

- **Flyway** for database migration management.
- **PostgreSQL** as the relational database.
- **OpenFeign** for declarative REST client creation.
- **Spring AOP** for aspect-oriented programming.
- **OpenAPI** for API documentation and UI.
- **MapStruct** for object mapping between DTOs and entities.
- **JWT** for secure token-based authentication.
- **HikariCP** for high-performance JDBC connection pooling.
- **Spring Web Services** for building SOAP web services.
- **Spring Security** with OAuth2 client support.
- **JPA** for persistence and ORM with Spring Data JPA.
- **Lombok** to reduce boilerplate code.

## Requirements

- **Java 17**
- **Maven 3.6+**
- **PostgreSQL 13+**

## Getting Started

### Clone the repository
```bash
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```


### Install Dependencies
Ensure you have all dependencies defined in your pom.xml. If using Maven, you can install them by running:

```bash
mvn clean install
```

### Database Setup
Create a PostgreSQL database:
```sql
CREATE DATABASE your_db_name;
```

### Configure your database credentials in
application.properties or application.yml:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=none
```

### Flyway Migrations
Flyway is used to manage database migrations. Ensure that your database is properly initialized by running the migrations:

```bash
mvn flyway:migrate
```

## Configuration
### Application Properties
Configure your application settings in src/main/resources/application.properties or application.yml. Below are some key configurations:
- Server Port:

```properties
server.port=8080
```
- JWT Configuration:

```properties
jwt.secret=your_secret_key
jwt.expirationMs=3600000
```
- OAuth2 Client Configuration:

```properties
spring.security.oauth2.client.registration.your-client-id.client-id=your-client-id
spring.security.oauth2.client.registration.your-client-id.client-secret=your-client-secret
```

## Building and Running
### Build the Project
##### Use Maven to build the project:

```bash
mvn clean package
```
### Run the Application
##### Run the Spring Boot application:

```bash
java -jar target/your-app-name.jar
```
- Alternatively, you can use the Maven Spring Boot plugin:

```bash
mvn spring-boot:run
```
## Testing
Run unit and integration tests:

```bash
mvn test
```

## API Documentation
The application uses Springdoc OpenAPI for API documentation.

After running the application, you can access the API documentation at:

```bash
http://localhost:8080/swagger-ui.html
```
For specific API documentation and testing, refer to the live Swagger UI hosted at:

- [Swagger](https://api.siriuslab.kz/swagger-ui/index.html#/api-one-c-contoller/getContext)

## Dependencies
Below is a list of key dependencies used in this project:

- Flyway: Version control for your database.
- PostgreSQL: Powerful, open-source object-relational database system.
- OpenFeign: Declarative REST client.
- Spring AOP: Aspect-oriented programming in Spring.
- Springdoc OpenAPI: Automated API documentation for Spring Boot projects.
- MapStruct: A Java annotation processor for generating type-safe bean mapping classes.
- Java JWT: Library for creating and verifying JSON Web Tokens.
- HikariCP: JDBC connection pool.
- Lombok: Library that helps to reduce boilerplate code in Java.
- Spring Web Services: Creating document-driven web services.
- Spring Data JPA: Data access abstraction for JPA-based repositories.
- Spring Security: Provides comprehensive security services for Java applications.
- Spring OAuth2 Client: Secure your application using OAuth2.
## License
This project is licensed under the MIT License. See the LICENSE file for details.

### Customization
- Replace placeholders like `your-repo-name`, `your_db_name`, `your_username`, etc., with actual values relevant to your project.
- Customize the **License** section according to your project's licensing.

This `README.md` provides a detailed overview of your Spring Boot application, including guidance on configuration, running, and accessing API documentation both locally and remotely via Swagger UI.