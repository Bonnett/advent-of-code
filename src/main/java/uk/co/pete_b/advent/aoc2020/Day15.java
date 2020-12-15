package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {
    public static int findIteration2020(final String input, final int target) {
        final List<Integer> sequence = Arrays.stream(input.split(",")).map(String::trim).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        final Map<Integer, Pair<Integer, Integer>> iterations = new HashMap<>();

        for (int i = 0; i < sequence.size(); i++) {
            final Integer instance = sequence.get(i);
            updateIterationHistory(iterations, instance, i);
        }

        int lastCalledNumber = sequence.get(sequence.size() - 1);

        for (int iteration = sequence.size(); iteration < target; iteration++) {
            if (iterations.containsKey(lastCalledNumber) && iterations.get(lastCalledNumber).getLeft() != null) {
                final Pair<Integer, Integer> lastTwoIterations = iterations.get(lastCalledNumber);
                final int newNumber = lastTwoIterations.getRight() - lastTwoIterations.getLeft();
                updateIterationHistory(iterations, newNumber, iteration);
                lastCalledNumber = newNumber;
            } else {
                updateIterationHistory(iterations, 0, iteration);
                lastCalledNumber = 0;
            }
        }

        return lastCalledNumber;
    }

    private static void updateIterationHistory(final Map<Integer, Pair<Integer, Integer>> iterations, final int numberCalled, final int iteration) {
        iterations.compute(numberCalled, (key, appearances) -> {
            if (appearances == null) {
                return Pair.of(null, iteration);
            } else {
                return Pair.of(appearances.getRight(), iteration);
            }
        });
    }
}
