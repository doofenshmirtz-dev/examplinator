package dev.doofenshmirtz.randomgen.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomService {
    private static final long UINT_MAX = 4294967295L;
    private static final SecureRandom random = new SecureRandom();

    public long getRandom(String minStr, String maxStr) {
        long min = 0;
        long max = UINT_MAX;

        if (minStr != null) {
            min = parseUnsigned(minStr, "min");
        }
        if (maxStr != null) {
            max = parseUnsigned(maxStr, "max");
        }

        if (min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }

        return min + (long)(random.nextDouble() * ((max - min) + 1));
    }

    private long parseUnsigned(String value, String name) {
        try {
            long parsed = Long.parseLong(value);
            if (parsed < 0 || parsed > UINT_MAX) {
                throw new IllegalArgumentException(name + " must be between 0 and " + UINT_MAX);
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be a valid unsigned integer");
        }
    }
}
