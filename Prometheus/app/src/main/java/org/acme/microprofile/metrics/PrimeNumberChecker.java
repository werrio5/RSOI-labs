package org.acme.microprofile.metrics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/")
public class PrimeNumberChecker {

    private long highestPrimeNumberSoFar = 2;
    private long notPrimeNumbersCount = 0;
    private long primeNumbersCount = 0;
    private long number3Count = 0;
    private long invalidNumbersCount = 0;

    @GET
    @Path("{number}")
    @Produces("text/plain")
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public String checkIfPrime(@PathParam long number) {
        if (number < 1) {
            notPrimeNumbersCount++;
            invalidNumbersCount++;
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            notPrimeNumbersCount++;
            return "1 is not prime.";
        }
        if (number == 2) {
            return "2 is prime.";
        }
        if (number % 2 == 0) {
            notPrimeNumbersCount++;
            return number + " is not prime, it is divisible by 2.";
        }
        if(number == 3) number3Count++;
        for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
            if (number % i == 0) {
                notPrimeNumbersCount++;
                return number + " is not prime, is divisible by " + i + ".";
            }
        }
        primeNumbersCount++;
        if (number > highestPrimeNumberSoFar) {
            highestPrimeNumberSoFar = number;
        }
        return number + " is prime.";
    }

    @Gauge(name = "highestPrimeNumberSoFar", unit = MetricUnits.NONE, description = "Highest prime number so far.")
    public Long highestPrimeNumberSoFar() {
        return highestPrimeNumberSoFar;
    }
    
    @Gauge(name = "notPrimeNumbersCount", unit = MetricUnits.NONE, description = "Not prime numbers count")
    public Long notPrimeNumbersCount() {
        return notPrimeNumbersCount;
    }

    @Gauge(name = "primeNumbersCount", unit = MetricUnits.NONE, description = "Prime numbers count")
    public Long primeNumbersCount() {
        return primeNumbersCount;
    }
    
    @Gauge(name = "number3Count", unit = MetricUnits.NONE, description = "3 count")
    public Long number3Count() {
        return number3Count;
    }
    
    @Gauge(name = "invalidNumbersCount", unit = MetricUnits.NONE, description = "invalid numbers count count")
    public Long invalidNumbersCount() {
        return invalidNumbersCount;
    }
}
