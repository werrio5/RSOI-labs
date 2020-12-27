FROM werrio5/jre11

RUN git clone https://github.com/werrio5/RSOI-labs/ \
 && cp /RSOI-labs/apps/lib/* /deployments/lib/ \
 && cp /RSOI-labs/apps/microprofile-health-quickstart-1.0-SNAPSHOT-runner.jar /deployments/app.jar 
  
ENTRYPOINT ["java", "-jar", "deployments/app.jar"]
