package uk.co.pete_b.advent.aoc2017;

import java.util.Arrays;

public class Day05 {

    public static int findNoOfStepsPart1(final String input) {
        int numSteps = 0;
        int currPointer = 0;
        int lastPointer = 0;

        final Integer[] offsets = getOffsets(input);

        while (currPointer < offsets.length) {
            int offset = offsets[currPointer];
            lastPointer += offset;
            offsets[currPointer]++;
            currPointer = lastPointer;

            numSteps++;
        }

        return numSteps;
    }

    public static int findNoOfStepsPart2(final String input) {
        int numSteps = 0;
        int currPointer = 0;
        int lastPointer = 0;

        final Integer[] offsets = getOffsets(input);

        while (currPointer < offsets.length) {
            int offset = offsets[currPointer];
            lastPointer += offset;
            if (offset >= 3) {
                offsets[currPointer]--;
            } else {
                offsets[currPointer]++;
            }

            currPointer = lastPointer;

            numSteps++;
        }

        return numSteps;
    }

    private static Integer[] getOffsets(String input) {
        return Arrays.stream(input.split("\r?\n")).map(Integer::parseInt).toArray(Integer[]::new);
    }
}
