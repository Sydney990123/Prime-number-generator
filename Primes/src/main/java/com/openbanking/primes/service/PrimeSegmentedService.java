package com.openbanking.primes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class PrimeSegmentedService {

    private static final Logger LOG = LoggerFactory.getLogger(PrimeSegmentedService.class);
    @Cacheable(value = "primes", key = "#low + '-' + #high", cacheManager = "myCacheManager")
    public ArrayList<Long> processSegment(long low, long high) {

        if (low > high) throw new IllegalArgumentException("Low could not be greater than high.");

        if (high < 0 || low < 0) throw new IllegalArgumentException("Invalid negative value.");

        LOG.info("start to get prime number in range {} to {} ", low, high);
        int limit = (int) Math.sqrt(high) + 1;
        ArrayList<Long> basePrimes = getBasePrimes(limit);

        ArrayList<Long> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) (high-low+1)];
        Arrays.fill(isPrime, true);

        for (long prime : basePrimes) {
            long start = Math.max(prime * prime, (low + prime - 1) / prime * prime);
            for (long j = start; j <= high; j += prime) {
                isPrime[(int) (j - low)] = false;
            }
        }

        for (long i = Math.max(2, low); i <= high; i++) {
            if (isPrime[(int) (i - low)]) {
                primes.add(i);
            }
        }

        return primes;
    }
    private static ArrayList<Long> getBasePrimes(int limit) {

        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        ArrayList<Long> primes = new ArrayList<>();
        for (int p = 3; p * p <= limit; p += 2)
            if (isPrime[p]) {
                for (int i = p * p; i <= limit; i += 2 * p) {
                    isPrime[i] = false;
                }
            }
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i])
                primes.add((long) i);
        }

        return primes;
    }
}
