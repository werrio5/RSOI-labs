#!/bin/sh
EXTERNAL_IP=$(ip addr show enp0s3 | grep -Po 'inet \K[\d.]+')
echo "self" $EXTERNAL_IP
export EXTERNAL_IP

CONSUL_URL="consul://192.168.1.8:8500"
echo "consul" $CONSUL_URL
export CONSUL_URL

#port
import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('', 0))
addr = s.getsockname()
echo "port" addr[1]
PORT=addr[1]
s.close()

export PORT

docker-compose up
