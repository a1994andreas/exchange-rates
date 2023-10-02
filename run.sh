#!/usr/bin/env bash

# Build
mvn clean -o && mvn install -DskipTests -e -U

# Run a Redis instance
docker run -d -p 6379:6379 redis/redis-stack-server:latest

java -jar ./target/exchange-rates-1.0-SNAPSHOT.jar

