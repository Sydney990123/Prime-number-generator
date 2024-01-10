package com.openbanking.primes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PrimeService {

    @Autowired
    private PrimeSegmentedService primeSegmentedService;

    @Value("${prime.segment.size: 100}")
    private int SEGMENT_SIZE;

    @Cacheable(value = "primes", key = "#number", cacheManager = "myCacheManager")
    public ArrayList<Long> getPrimes(long number) {

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

}
