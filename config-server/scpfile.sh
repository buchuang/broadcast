#!/usr/bin/env bash
mv ./target/config-server-0.0.1-SNAPSHOT.jar ./target/config-server.jar
scp ./target/config-server.jar root@192.168.100.235:/root/project