https://www.keycloak.org/getting-started/getting-started-docker

curl -X POST http://192.168.122.11:8080/auth/realms/master/protocol/openid-connect/token \
    --user backend-service:1c41e670-145e-44b6-926e-926e7a31aab2 \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=user&password=1&otp=315379&grant_type=password' 
