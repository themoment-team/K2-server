#!/bin/bash

echo "======existing container clean======="
docker rm the-moment-app

echo "====gradle clean build======"
./gradlew clean build

echo "======docker-build======="
docker build . -t the-moment-server

echo "========docker run========"
docker run -it -p 5000:8080 --name the-moment-app the-moment-server