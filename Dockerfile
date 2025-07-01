# === Stage 1: Build the JAR with Maven (only if not using Nexus)
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src/ src/
RUN mvn clean package

# === Stage 2: Final Image
FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8083

ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD
ARG NEXUS_URL

# If Nexus URL is provided, download the jar. Otherwise, use the jar from builder stage.
RUN if [ -n "$NEXUS_URL" ]; then \
      echo "Downloading from Nexus..."; \
      apk add --no-cache curl; \
      curl -L -u "${NEXUS_USERNAME}:${NEXUS_PASSWORD}" -o app.jar "${NEXUS_URL}"; \
    else \
      echo "Using JAR built in builder stage..."; \
      mkdir -p /tmp; \
      cp /tmp/built.jar app.jar; \
    fi

# Copy built jar if Nexus URL is not provided
COPY --from=builder /app/target/*.jar /tmp/built.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
