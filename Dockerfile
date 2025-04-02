FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8083
ADD target/4Twin4-G3-Foyer-3.0.0.jar foyer-3.0.0.jar
ENTRYPOINT ["java","-jar","foyer-3.0.0.jar"]

