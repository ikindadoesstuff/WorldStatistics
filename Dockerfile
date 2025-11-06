FROM eclipse-temurin:17-jdk
COPY target/WorldStatistics-0.0.2-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

