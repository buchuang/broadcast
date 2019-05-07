#!/usr/bin/env bash
mv ./target/plate-server-0.0.1-SNAPSHOT.jar ./target/plate-server.jar
scp ./target/plate-server.jar root@192.168.100.235:/root/project