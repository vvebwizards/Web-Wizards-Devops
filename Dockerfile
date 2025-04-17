# Stage 1: Build the project with Maven (tests will run)
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies to speed up subsequent builds
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the jar
COPY src/ src/
RUN mvn clean package

# Stage 2: Create a lightweight runtime image
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Use a wildcard to copy the generated jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (8083)
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]