#!/usr/bin/env bash
mv ./target/eureka-0.0.1-SNAPSHOT.jar ./target/eureka.jar
scp ./target/eureka.jar root@192.168.100.235:/root/project