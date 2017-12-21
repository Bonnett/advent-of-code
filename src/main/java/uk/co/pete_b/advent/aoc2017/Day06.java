package uk.co.pete_b.advent.aoc2017;

import java.util.*;
import java.util.stream.Collectors;

public class Day06 {

    public static int numberTillInfiniteLoop(final String input) {
        int numSteps = 0;

        final int[] blocks = getInitialBytes(input);

        final Set<List<Integer>> history = new HashSet<>();

        List<Integer> converted = convertToBoxedList(blocks);
        while (!history.contains(converted)) {
            history.add(converted);
            numSteps++;

            int largestIndex = findLargestIndex(blocks);
            int largestValue = blocks[largestIndex];
            blocks[largestIndex] = 0;
            for (int i = 0; i < largestValue; i++) {
                int nextIndex = (largestIndex + i + 1) % blocks.length;
                blocks[nextIndex]++;
            }

            converted = convertToBoxedList(blocks);
        }

        return numSteps;
    }

    public static int cyclesFromInfiniteLoop(final String input) {
        int numSteps = 0;

        final int[] blocks = getInitialBytes(input);

        final Map<List<Integer>, Integer> historyList = new HashMap<>();

        List<Integer> converted = convertToBoxedList(blocks);
        while (!historyList.containsKey(converted)) {
            historyList.put(converted, ++numSteps);

            int largestIndex = findLargestIndex(blocks);
            int largestValue = blocks[largestIndex];
            blocks[largestIndex] = 0;
            for (int i = 0; i < largestValue; i++) {
                int nextIndex = (largestIndex + i + 1) % blocks.length;
                blocks[nextIndex]++;
            }

            converted = convertToBoxedList(blocks);
        }

        return (numSteps + 1) - historyList.get(converted);
    }

    private static int[] getInitialBytes(String input) {
        return Arrays.stream(input.split("\\s")).mapToInt(Integer::parseInt).toArray();
    }

    private static List<Integer> convertToBoxedList(final int[] input) {
        return Arrays.stream(input).boxed().collect(Collectors.toList());
    }

    private static int findLargestIndex(final int[] input) {
        int largestIndex = 0;
        int largest = input[largestIndex];
        for (int i = 1; i < input.length; i++) {
            if (input[i] > largest) {
                largest = input[i];
                largestIndex = i;
            }
        }

        return largestIndex;
    }
}
