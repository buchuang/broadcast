#!/usr/bin/env bash
mv ./target/user-server-0.0.1-SNAPSHOT.jar ./target/user-server.jar
scp ./target/user-server.jar root@192.168.100.235:/root/project