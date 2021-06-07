#!/bin/bash

./mvnw clean install
docker-compose build
docker-compose up -d