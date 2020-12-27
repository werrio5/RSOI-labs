#!/bin/sh
EXTERNAL_IP=$(ip addr show enp0s3 | grep -Po 'inet \K[\d.]+')
echo "self" $EXTERNAL_IP
export EXTERNAL_IP

CONSUL_URL="consul://192.168.1.8:8500"
echo "consul" $CONSUL_URL
export CONSUL_URL

#port

POST=$(comm -23 <(seq 49152 65535 | sort) <(ss -Htan | awk '{print $4}' | cut -d':' -f2 | sort -u) | shuf | head -n 3)
echo "port " $PORT
export PORT

docker-compose up
