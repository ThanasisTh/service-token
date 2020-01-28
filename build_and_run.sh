#!/bin/bash

set -e

sudo docker stop service-token

mvn clean test package

sudo docker build --tag service-token .
sudo docker-compose up -d