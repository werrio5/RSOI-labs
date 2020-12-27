#!/bin/sh
docker build . -t dockerlab
docker run dockerlab
