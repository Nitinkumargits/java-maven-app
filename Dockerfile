FROM eclipse-temurin:8-jdk-alpine

EXPOSE 8080

COPY ./target/java-app-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "java-app-1.0-SNAPSHOT.jar"]
