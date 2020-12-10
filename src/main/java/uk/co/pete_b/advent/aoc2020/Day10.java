package uk.co.pete_b.advent.aoc2020;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    public static int calculateJoltDifference(final String adapters) {
        final List<Integer> sequence = getSequence(adapters);

        int currentValue = 0;
        int differenceOfOne = 0;

        // Factor in final step up of 3 at the end
        int differenceOfThree = 1;

        for (int adapter : sequence) {
            int diff = adapter - currentValue;
            if (diff == 1) {
                differenceOfOne++;
            } else if (diff == 3) {
                differenceOfThree++;
            }

            currentValue = adapter;
        }

        return differenceOfOne * differenceOfThree;
    }

    private static List<Integer> getSequence(String adapters) {
        final List<Integer> numberList = Arrays.stream(adapters.split("\n")).mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());
        // Add start of 0
        numberList.add(0, 0);
        return numberList;
    }

    public static double calculatePermutations(final String adapters) {
        final List<Integer> numberList = getSequence(adapters);

        int numberOfTriplets = 0;
        int numberOfQuadruples = 0;
        int numberOfQuintets = 0;

        for (int i = 0; i < numberList.size() - 4; i++) {
            if (numberList.get(i) + 4 == numberList.get(i + 4)) {
                numberOfQuintets++;
                i = i + 4;
            } else if (numberList.get(i) + 3 == numberList.get(i + 3)) {
                numberOfQuadruples++;
                i = i + 3;
            } else if (numberList.get(i) + 2 == numberList.get(i + 2)) {
                numberOfTriplets++;
                i = i + 2;
            }
        }

        // Can navigate a triplet 2 ways, quadruplet 4 ways and quintet 7 ways
        return Math.pow(2, numberOfTriplets) * Math.pow(4, numberOfQuadruples) * Math.pow(7, numberOfQuintets);
    }
}
