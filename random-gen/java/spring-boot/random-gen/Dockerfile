# ======== STAGE 1: Build ========
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ======== STAGE 2: Runtime ========
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
