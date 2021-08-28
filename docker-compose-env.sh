#!/bin/bash
echo "====mvn clean======"
./mvnw clean

echo "=====mvn compile====="
./mvnw compile

echo "======mvn package======"
./mvnw package

echo "======docker-compose build======"
docker-compose build

echo "======docker-compose up======="
docker-compose up