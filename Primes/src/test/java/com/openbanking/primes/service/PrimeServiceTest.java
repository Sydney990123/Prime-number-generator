package com.openbanking.primes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PrimeServiceTest {
    @Mock
    private PrimeSegmentedService primeSegmentedService;

    @InjectMocks
    private PrimeService primeService;

    @Test
    public void getPrimes_whenNumberBelowTwo_shouldReturnEmptyList() {
        long number = 1L;

        ArrayList<Long> primes = primeService.getPrimes(number);

        assertTrue(primes.isEmpty());
        verify(primeSegmentedService, times(0)).processSegment(anyLong(), anyLong());
    }
    @Test
    public void getPrimes_whenBelowSegmentedSize() {
        long number = 10L;
        ReflectionTestUtils.setField(primeService, "SEGMENT_SIZE", 100);
        Mockito.when(primeSegmentedService.processSegment(1L, number)).thenReturn(new ArrayList<>(Arrays.asList(2L,3L,5L,7L)));

        ArrayList<Long> primes = primeService.getPrimes(number);

        assertEquals(new ArrayList<>(Arrays.asList(2L,3L,5L,7L)), primes);
        verify(primeSegmentedService, times(1)).processSegment(anyLong(), anyLong());
    }

    @Test
    public void getPrimes_whenAboveSegmentedSize() {
        long number = 10L;
        int segmentSize = 5;
        ReflectionTestUtils.setField(primeService, "SEGMENT_SIZE", segmentSize);
        Mockito.when(primeSegmentedService.processSegment(1L, segmentSize)).thenReturn(new ArrayList<>(Arrays.asList(2L,3L,5L)));
        Mockito.when(primeSegmentedService.processSegment(6L, number)).thenReturn(new ArrayList<>(Arrays.asList(7L)));

        ArrayList<Long> primes = primeService.getPrimes(number);

        assertEquals(new ArrayList<>(Arrays.asList(2L,3L,5L,7L)), primes);
        verify(primeSegmentedService, times(2)).processSegment(anyLong(), anyLong());
    }
}