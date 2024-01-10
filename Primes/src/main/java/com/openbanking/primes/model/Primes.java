package com.openbanking.primes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Primes {
    private long Initial;
    private ArrayList<Long> Primes;
}
