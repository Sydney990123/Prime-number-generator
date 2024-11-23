package com.openbanking.primes.web;

import com.openbanking.primes.model.Primes;
import com.openbanking.primes.service.PrimeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.openbanking.primes.util.KafkaProperties.KAFKA_TOPIC;

@RestController
public class PrimeController {

    private final PrimeService primeService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public PrimeController(PrimeService primeService, KafkaTemplate<String, String> kafkaTemplate) {
        this.primeService = primeService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hello, this is a prime number generator.");
    }


    @GetMapping(value = "/primes/{number}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPrimes(@PathVariable long number) {
        sendKafkaMessage(KAFKA_TOPIC, String.format("Received request to calculate all prime numbers for number %s", number));
        return ResponseEntity.ok(new Primes(number, primeService.getPrimes(number)));
    }

    public void sendKafkaMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
