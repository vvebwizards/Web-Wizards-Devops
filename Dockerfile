FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8083
ADD target/foyer-3.0.0.jar foyer-3.0.0.jar
ENTRYPOINT ["java","-jar","/foyer-3.0.0.jar"]

# Copy the JAR file from the local machine to the container
COPY target/foyer-3.0.0.jar /app/foyer-3.0.0.jar