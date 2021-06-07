FROM maven:3.6.1-jdk-8-slim as build

LABEL maintainer="mihajlo.nesic@atessoft.rs"

WORKDIR /playerteam
COPY . /playerteam/

EXPOSE 8082
ENTRYPOINT ["java","-jar","./target/player-team-service.jar"]
