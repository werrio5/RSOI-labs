#!/bin/sh
export EXTERNAL_IP=$(ip addr show enp0s3 | grep -Po 'inet \K[\d.]+')
exec docker-compose $@
