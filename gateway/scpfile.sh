#!/usr/bin/env bash
mv ./target/gateway-0.0.1-SNAPSHOT.jar ./target/gateway.jar
scp ./target/gateway.jar root@192.168.100.235:/root/project