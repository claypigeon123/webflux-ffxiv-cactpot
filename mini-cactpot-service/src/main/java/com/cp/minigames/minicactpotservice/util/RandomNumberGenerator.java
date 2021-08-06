package com.cp.minigames.minicactpotservice.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomNumberGenerator {
    private final SecureRandom rnd;

    public RandomNumberGenerator() {
        this.rnd = new SecureRandom();
    }

    public int generate(int min, int max) {
        return rnd.nextInt((max - min) + 1) + min;
    }
}
