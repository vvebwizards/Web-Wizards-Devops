FROM maven:3.8.4-openjdk-17
WORKDIR /foyer
EXPOSE 8083
RUN apt-get update && apt-get install -y curl
RUN curl -L -u "${NEXUS_USERNAME}:${NEXUS_PASSWORD}" -o foyer-3.0.0.jar "${NEXUS_URL}"
ENTRYPOINT ["java","-jar","foyer-3.0.0.jar"]

