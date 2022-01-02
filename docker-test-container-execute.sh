#!/bin/bash

echo "해당 sh 파일은 docker-container 를 통해 test 서버를 실행할 때 사용합니다. "

echo "======existing container clean======="
docker rm k2-server

echo "====gradle clean build======"
./gradlew clean build

echo "======docker-build======="
docker build --no-cache . -t k2-server

echo "========docker run========"
docker run -it -p 5000:7890 --name k2-server k2-server