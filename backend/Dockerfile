FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
COPY target/foyer-3.0.0.jar foyer-3.0.0.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "foyer-3.0.0.jar"]
