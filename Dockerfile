# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Add the application's jar to the container
COPY target/superhero-management-service-0.0.1-SNAPSHOT.jar superhero-management-service.jar

EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "superhero-management-service.jar"]