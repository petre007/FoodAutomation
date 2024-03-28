FROM openjdk:17-jdk-alpine

WORKDIR /usr/app/

MAINTAINER petredumitru1@gmail.com

COPY target/flux-0.0.1-SNAPSHOT.jar flux.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "flux.jar"]