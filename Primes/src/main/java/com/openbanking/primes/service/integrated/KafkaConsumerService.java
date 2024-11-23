package com.openbanking.primes.service.integrated;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.openbanking.primes.util.KafkaProperties.KAFKA_GROUP_ID;
import static com.openbanking.primes.util.KafkaProperties.KAFKA_TOPIC;


@Service
public class KafkaConsumerService {

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
