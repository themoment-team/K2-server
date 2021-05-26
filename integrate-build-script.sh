#!/bin/bash
echo "====git pull======"
git pull origin master

echo "====mvn clean======"
mvn clean

echo "=====mvn compile====="
mvn compile

echo "======mvn package======"
mvn package