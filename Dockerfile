FROM openjdk:17-oracle
COPY ./target/WorldStatistics-0.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "WorldStatistics-0.0.1-jar-with-dependencies.jar"]