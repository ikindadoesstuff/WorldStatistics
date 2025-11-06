FROM eclipse-temurin:17-jdk
COPY target/WorldStatistics.jar /tmp/app.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "/app.jar"]

