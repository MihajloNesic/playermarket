FROM maven:3.6.1-jdk-8-slim as build

LABEL maintainer="mihajlo.nesic@atessoft.rs"

WORKDIR /gateway
COPY . /gateway/

EXPOSE 8080
ENTRYPOINT ["java","-jar","./target/gateway-service.jar"]
