FROM openjdk:11-jre-slim

WORKDIR /
COPY services/service-catalog-searcher/target/catalog-searcher-*.jar /catalog-searcher.jar

ENTRYPOINT ["java", "-jar", "catalog-searcher.jar"]