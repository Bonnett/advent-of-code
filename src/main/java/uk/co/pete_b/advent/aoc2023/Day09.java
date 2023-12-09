package uk.co.pete_b.advent.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 {
    public static Answer sumOfExtrapolatedValues(final List<String> sequences) {
        int leftMostSum = 0;
        int rightMostSum = 0;
        for (String sequenceString : sequences) {
            int sequenceSum = 0;
            final List<Integer> sequence = Arrays.stream(sequenceString.split(" ")).map(Integer::parseInt).toList();
            final List<Integer> firstDifference = new ArrayList<>();
            final List<Integer> currentSequence = new ArrayList<>(sequence);
            while (true) {
                boolean allZero = true;
                final List<Integer> difference = new ArrayList<>();
                for (int i = 0; i < currentSequence.size() - 1; i++) {
                    int currentDifference = currentSequence.get(i + 1) - currentSequence.get(i);
                    allZero &= currentDifference == 0;
                    difference.add(currentDifference);
                }
                sequenceSum += currentSequence.get(currentSequence.size() - 1);
                currentSequence.clear();
                currentSequence.addAll(difference);
                firstDifference.add(0, difference.get(0));
                if (allZero) {
                    break;
                }
            }

            int leftMostDifference = 0;
            for (Integer difference : firstDifference) {
                leftMostDifference = difference - leftMostDifference;
            }

            leftMostSum += sequence.get(0) - leftMostDifference;
            rightMostSum += sequenceSum;
        }

        return new Answer(leftMostSum, rightMostSum);
    }

    public record Answer(int leftmostSum, int rightmostSum) {
    }
}
