#!/bin/sh
docker build . -t dockerlab
docker run -p 8080:8080 dockerlab