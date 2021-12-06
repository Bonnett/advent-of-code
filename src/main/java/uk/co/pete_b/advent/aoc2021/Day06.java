package uk.co.pete_b.advent.aoc2021;

import java.util.Arrays;

public class Day06 {
    public static long calculateLanternFish(final String fishSetup, int iterations) {
        long[] school = new long[9];
        for (String value : fishSetup.split(",")) {
            school[Integer.parseInt(value)]++;
        }

        for (int iter = 0; iter < iterations; iter++) {
            final long[] newSchool = new long[9];

            long newFish = school[0];
            System.arraycopy(school, 1, newSchool, 0, school.length - 1);
            newSchool[8] = newFish;
            newSchool[6] += newFish;
            school = newSchool;
        }

        return Arrays.stream(school).sum();
    }
}
