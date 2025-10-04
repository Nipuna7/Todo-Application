FROM openjdk:23-jdk
WORKDIR /app
COPY target/todo-app.jar todo-app.jar
ENTRYPOINT ["java", "-jar", "todo-app.jar"]