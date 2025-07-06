# ---------- Stage 1: Build the application ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build using native Quarkus output layout
RUN mvn clean package -DskipTests -Dquarkus.package.type=fast-jar

# ---------- Stage 2: Run the application ----------
FROM registry.access.redhat.com/ubi9/openjdk-21:1.21

ENV LANGUAGE='en_US:en'

# Copy the application layers built in Stage 1
COPY --chown=185 --from=builder /app/target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 --from=builder /app/target/quarkus-app/*.jar /deployments/
COPY --chown=185 --from=builder /app/target/quarkus-app/app/ /deployments/app/
COPY --chown=185 --from=builder /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]
