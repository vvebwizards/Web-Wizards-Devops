FROM maven:3.8.4-openjdk-17

WORKDIR /foyer
EXPOSE 8083

ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD
ARG NEXUS_URL

RUN if [ -n "$NEXUS_URL" ]; then \
      echo "Downloading from Nexus..."; \
      curl -L -u "${NEXUS_USERNAME}:${NEXUS_PASSWORD}" -o foyer.jar "${NEXUS_URL}"; \
    else \
      echo "No Nexus URL provided, expecting local jar to be added later."; \
    fi

ADD target/4Twin4-G3-Foyer-3.0.0.jar foyer.jar

ENTRYPOINT ["java", "-jar", "foyer.jar"]
