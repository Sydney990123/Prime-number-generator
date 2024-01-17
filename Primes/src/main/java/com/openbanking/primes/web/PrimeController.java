package com.openbanking.primes.web;

import com.openbanking.primes.model.Primes;
import com.openbanking.primes.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeController {

    @Autowired
    private PrimeService primeService;

    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hello, this is a prime number generator.");
    }


    @GetMapping(value = "/primes/{number}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPrimes(@PathVariable long number) {
            return ResponseEntity.ok(new Primes(number, primeService.getPrimes(number)));
    }

}
