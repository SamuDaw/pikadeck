FROM openjdk:17-jdk-alpine
COPY target/Pokemon-0.0.1-SNAPSHOT.war pokemon.war
COPY img /Pokemon/Pokemon/src/main/resources/static/img/
ENTRYPOINT ["java", "-jar", "pokemon.war"]
