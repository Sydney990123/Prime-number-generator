package com.openbanking.primes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
public class PrimeSegmentedService {

    @Cacheable(value = "primes", key = "#low + '-' + #high", cacheManager = "myCacheManager")
    public ArrayList<Long> processSegment(long low, long high) {

        validateInput(low, high);

        log.info("start to get prime number in range {} to {} ", low, high);
        int limit = (int) Math.sqrt(high) + 1;
        ArrayList<Long> basePrimes = getBasePrimes(limit);

        boolean[] isPrime = new boolean[(int) (high-low+1)];
        Arrays.fill(isPrime, true);

        markMultiplesAsNonPrime(low, high, basePrimes, isPrime);

        return collectPrimes(low, high, isPrime);
    }

    private void validateInput(long low, long high) {
        if (low > high) throw new IllegalArgumentException("Low could not be greater than high.");

        if (high < 0 || low < 0) throw new IllegalArgumentException("Invalid negative value.");
    }

    private ArrayList<Long> getBasePrimes(int limit) {

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

        for (int p = 2; p <= limit; p++) {
            if (isPrime[p])
                primes.add((long) p);
        }

        return primes;
    }

    private void markMultiplesAsNonPrime(long low, long high, ArrayList<Long> basePrimes, boolean[] isPrime) {
        for (long prime : basePrimes) {
            long start = Math.max(prime * prime, (low + prime - 1) / prime * prime);
            for (long i = start; i <= high; i += prime) {
                isPrime[(int) (i - low)] = false;
            }
        }
    }

    private static ArrayList<Long> collectPrimes(long low, long high, boolean[] isPrime) {
        ArrayList<Long> primes = new ArrayList<>();
        for (long i = Math.max(2, low); i <= high; i++) {
            if (isPrime[(int) (i - low)]) {
                primes.add(i);
            }
        }

        return primes;
    }
}
