package com.openbanking.primes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PrimeSegmentedServiceTest {
    public static final String ASSERTION_FAILURE_MESSAGE = "Expected IllegalArgumentException to throw, but it didn't";
    @InjectMocks
    private PrimeSegmentedService primeSegmentedService;

    @Test
    void processSegment_whenSuccess() {
        long low = 0L;
        long high = 10L;
        ArrayList<Long> primes = primeSegmentedService.processSegment(low, high);

        assertEquals(new ArrayList<>(Arrays.asList(2L,3L,5L,7L)), primes);
    }

    @Test
    void processSegment_whenSameLowAndHigh() {
        long low = 0L;
        long high = 0L;
        ArrayList<Long> primes = primeSegmentedService.processSegment(low, high);

        assertEquals(new ArrayList<>(), primes);
    }

    @Test
    void processSegment_whenLowGreaterThanHigh() {
        long low = 12L;
        long high = 10L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            primeSegmentedService.processSegment(low, high);
        }, ASSERTION_FAILURE_MESSAGE);

        String expectedExceptionMessage = "Low could not be greater than high.";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void processSegment_whenLowAsNegativeValue() {
        long low = -1L;
        long high = 10L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            primeSegmentedService.processSegment(low, high);
        }, ASSERTION_FAILURE_MESSAGE);

        String expectedExceptionMessage = "Invalid negative value.";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void processSegment_whenHighAsNegativeValue() {
        long low = -11L;
        long high = -10L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            primeSegmentedService.processSegment(low, high);
        }, ASSERTION_FAILURE_MESSAGE);

        String expectedExceptionMessage = "Invalid negative value.";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }
}