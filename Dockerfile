# ─── Build Stage ───────────────────────────────────────────────────────────────
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# copy just enough to cache Maven deps when pom.xml doesn’t change
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy the rest of your source and package
COPY src ./src
RUN mvn clean package -DskipTests

# ─── Run Stage ─────────────────────────────────────────────────────────────────
# switched to the slimmer, jammy-based Temurin 17 JRE
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# grab the fat JAR from our build stage
COPY --from=build /app/target/todo-app-backend-0.0.1-SNAPSHOT.jar app.jar

# match your Spring Boot default
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
