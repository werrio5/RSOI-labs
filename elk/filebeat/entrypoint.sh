#!/bin/sh
systemctl start filebeat
java -jar /deployments/app.jar
