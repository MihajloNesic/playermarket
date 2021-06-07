FROM maven:3.6.1-jdk-8-slim as build

LABEL maintainer="mihajlo.nesic@atessoft.rs"

WORKDIR /transfer
COPY . /transfer/

EXPOSE 8083
ENTRYPOINT ["java","-jar","./target/transfer-service.jar"]
