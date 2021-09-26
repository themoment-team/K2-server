#!/bin/bash
echo "====gradle clean build======"
./gradlew clean build

echo "======docker-compose up======="
docker-compose up --build