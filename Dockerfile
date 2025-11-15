FROM eclipse-temurin:17-jdk
COPY target/WorldStatistics.jar /tmp
ENTRYPOINT ["java", "-jar", "/tmp/WorldStatistics.jar", "db:3306"]

