## Superhero Management Service

This is a Spring Boot application that provides RESTful APIs to manage superhero entities. The application is built using Java, Spring Boot, and H2 Database. It also includes Swagger for API documentation and Docker for containerization.

### Features

- Create new superhero instances
- Retrieve superhero instances
- Global exception handling
- Dockerized for easy deployment
- [Swagger UI](http://localhost:8080/swagger-ui.html) for API documentation

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- Docker


### Getting Started

#### Clone the Repository

```bash
git clone https://github.com/nehaparmar2506/superhero-management-service.git
cd superhero-management-service
```
### Build and Run the Application
##### Using Maven
```bash
mvn clean install
mvn spring-boot:run
```
##### Using Docker
###### 1.Build the Project:
```bash
mvn clean install
```
###### 2.Build the Docker Image:
```bash
docker build -t superhero-management-service .
```
###### 3.Run the Docker Container:
```bash
docker-compose up 
```

#### Access the Application
- API Documentation: Open http://localhost:8080/swagger-ui.html in your web browser to view the Swagger UI and interact with the APIs.