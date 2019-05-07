#!/usr/bin/env bash
mv ./target/chatroom-0.0.1-SNAPSHOT.jar ./target/chatroom.jar
scp ./target/chatroom.jar root@192.168.100.235:/root/project