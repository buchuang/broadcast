#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip=true -U
docker build -t hub.c.163.com/bu94450/team .
docker push hub.c.163.com/bu94450/team
