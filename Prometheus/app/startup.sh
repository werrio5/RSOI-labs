#!/bin/sh
EXTERNAL_IP=$(ip addr show enp0s3 | grep -Po 'inet \K[\d.]+')
echo "self" $EXTERNAL_IP
export EXTERNAL_IP

CONSUL_URL="consul://192.168.1.12:8500"
echo "consul" $CONSUL_URL
export CONSUL_URL

docker-compose up