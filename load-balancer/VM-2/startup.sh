#!/bin/sh
EXTERNAL_IP=$(ip addr show enp0s3 | grep -Po 'inet \K[\d.]+')
echo "self" $EXTERNAL_IP
export EXTERNAL_IP

CONSUL_URL="consul://192.168.1.8:8500"
echo "consul" $CONSUL_URL
export CONSUL_URL

#port

PORT=0
for i in {50000..60000}
do
  PORT=i
  RES=$(ss -ln src :$PORT | grep $PORT)
  if [RES==""]
  then
     break    
done

echo "port " $PORT
export PORT

docker-compose up
