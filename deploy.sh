#!/usr/bin/env bash

REPOSITORY=/opt/Application
cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
sudo nohup java -jar $JAR_PATH -Djava.security.egd=file:/dev/./urandom &

CURRENT_PID=$(pgrep -f $APP_NAME)
echo "> $CURRENT_PID 에서 실행중"