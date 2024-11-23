package com.openbanking.primes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PrimeService {

    private final PrimeSegmentedService primeSegmentedService;

    @Value("${prime.segment.size: 100}")
    private int SEGMENT_SIZE;


    public PrimeService(PrimeSegmentedService primeSegmentedService) {
        this.primeSegmentedService = primeSegmentedService;
    }
    @Cacheable(value = "primes", key = "#number", cacheManager = "myCacheManager")
    public ArrayList<Long> getPrimes(long number) {
        validateInput(number);

        if (number < 2) {
            return new ArrayList<>();
        }

        ArrayList<Long> primes = new ArrayList<>();

        for (long start = 1L; start <= number; start += SEGMENT_SIZE) {
            long end = Math.min(start + SEGMENT_SIZE - 1, number);
            primes.addAll(primeSegmentedService.processSegment(start, end));
        }

        return primes;
    }

    private void validateInput(long number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Please provide a positive number.");
        }
    }

}
