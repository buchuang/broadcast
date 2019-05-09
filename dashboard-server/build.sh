#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip=true -U
docker build -t registry.cn-hangzhou.aliyuncs.com/broadcast/dashboard-server:1.0 .
docker push registry.cn-hangzhou.aliyuncs.com/broadcast/dashboard-server:1.0
