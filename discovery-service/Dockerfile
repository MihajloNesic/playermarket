FROM maven:3.6.1-jdk-8-slim as build

LABEL maintainer="mihajlo.nesic@atessoft.rs"

WORKDIR /discovery
COPY . /discovery/

EXPOSE 8081
ENTRYPOINT ["java","-jar","./target/discovery-service.jar"]
