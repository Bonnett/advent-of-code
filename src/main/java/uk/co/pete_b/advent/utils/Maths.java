package uk.co.pete_b.advent.utils;

import java.util.Arrays;

public class Maths {
    public static long lcm(long... numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }
}
