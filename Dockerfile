FROM openjdk:17-jdk-slim
COPY target/WorldStatistics-0.0.1-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

