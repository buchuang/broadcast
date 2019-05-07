#!/usr/bin/env bash
mv ./target/dashboard-server-0.0.1-SNAPSHOT.jar ./target/dashboard-server.jar
scp ./target/dashboard-server.jar root@192.168.100.235:/root/project