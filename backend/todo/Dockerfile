# FROM openjdk:23-jdk
# WORKDIR /app
# COPY target/todo-app.jar todo-app.jar
# ENTRYPOINT ["java", "-jar", "todo-app.jar"]


# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .

COPY src ./src
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/todo-app.jar ./todo-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todo-app.jar"]
