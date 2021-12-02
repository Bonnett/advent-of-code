package uk.co.pete_b.advent.aoc2021;

import java.util.List;

public class Day01 {
    public static int calculateNumberOfIncreases(final List<Integer> depthChanges) {
        int numberOfIncreases = 0;
        int currentDepth = depthChanges.get(0);
        for (int i = 1; i < depthChanges.size(); i++) {
            final int nextDepth = depthChanges.get(i);
            if (currentDepth < nextDepth) {
                numberOfIncreases++;
            }
            currentDepth = nextDepth;
        }

        return numberOfIncreases;
    }
    public static int calculateNumberOfWindowIncreases(final List<Integer> depthChanges) {
        int numberOfIncreases = 0;

        int currTotal = depthChanges.subList(0, 3).stream().mapToInt(x -> x).sum();
        for (int i = 1; i < depthChanges.size() - 2; i++) {
            int nextTotal = depthChanges.subList(i, i + 3).stream().mapToInt(x -> x).sum();
            if (currTotal < nextTotal) {
                numberOfIncreases++;
            }
            currTotal = nextTotal;
        }

        return numberOfIncreases;
    }
}
