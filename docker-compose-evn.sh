#!/bin/bash
echo "====mvn clean======"
mvn clean

echo "=====mvn compile====="
mvn compile

echo "======mvn package======"
mvn package

echo "======docker-compose build======"
docker-compose build

echo "======docker-compose up======="
docker-compose up