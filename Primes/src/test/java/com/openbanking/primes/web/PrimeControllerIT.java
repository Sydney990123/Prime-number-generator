package com.openbanking.primes.web;

import com.openbanking.primes.model.Primes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrimeControllerIT {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetHomePage() {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Hello, this is a prime number generator.", responseEntity.getBody());
    }

    @Test
    public void testGetPrimes_acceptJson() {
        long number = 11L;
        String url = url(number);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ResponseEntity<Primes> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Primes.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("application/json", responseEntity.getHeaders().getContentType().toString());

        Primes primes = responseEntity.getBody();
        assertNotNull(primes);
        assertEquals(number, primes.getInitial());
        assertEquals(new ArrayList<>(Arrays.asList(2L,3L,5L,7L,11L)), primes.getPrimes());
    }

    @Test
    public void testGetPrimes_acceptXml() {
        long number = 11L;
        String url = url(number);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

        ResponseEntity<Primes> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Primes.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("application/xml", responseEntity.getHeaders().getContentType().toString());

        Primes primes = responseEntity.getBody();
        assertNotNull(primes);
        assertEquals(number, primes.getInitial());
        assertEquals(new ArrayList<>(Arrays.asList(2L,3L,5L,7L,11L)), primes.getPrimes());
    }

    @Test
    public void testGetPrimes_given1_returnEmpty() {
        long number = 1L;
        String url = url(number);

        ResponseEntity<Primes> responseEntity = restTemplate.getForEntity(url, Primes.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("application/json", responseEntity.getHeaders().getContentType().toString());

        Primes primes = responseEntity.getBody();
        assertNotNull(primes);
        assertEquals(number, primes.getInitial());
        assertEquals(new ArrayList<>(), primes.getPrimes());
    }

    @Test
    public void testGetPrimes_givenLargeNumber() {
        long number = Integer.MAX_VALUE/10;
        String url = url(number);

        ResponseEntity<Primes> responseEntity = restTemplate.getForEntity(url, Primes.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("application/json", responseEntity.getHeaders().getContentType().toString());

        Primes primes = responseEntity.getBody();
        assertNotNull(primes);
        assertEquals(number, primes.getInitial());
        assertTrue(primes.getPrimes().contains(199L));
    }

    @Test
    public void testGetPrimes_givenNegativeNumber_returnBadRequest() {
        long number = -1L;
        String url = url(number);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void testGetPrimes_givenNonNumericValue_returnBadRequest() {
        String nonNumeric = "abc";
        String url = "http://localhost:" + port + "/primes/" + nonNumeric;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private String url (long number) {
        return "http://localhost:" + port + "/primes/" + number;
    }
}