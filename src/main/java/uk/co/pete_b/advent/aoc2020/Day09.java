package uk.co.pete_b.advent.aoc2020;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 {
    public static long getFirstInvalidSum(final String sequenceStr, final int preambleLength) {
        return getFirstInvalidSum(getSequence(sequenceStr), preambleLength);
    }

    private static long getFirstInvalidSum(final long[] sequence, final int preambleLength) {
        final long[] workingSet = new long[preambleLength];
        if (preambleLength >= 0) {
            System.arraycopy(sequence, 0, workingSet, 0, preambleLength);
        }

        for (int index = preambleLength; index < sequence.length; index++) {
            boolean found = false;
            for (int i = 0; !found && i < workingSet.length; i++) {
                for (int j = i + 1; j < workingSet.length; j++) {
                    if (workingSet[i] + workingSet[j] == sequence[index]) {
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                return sequence[index];
            }

            workingSet[index % preambleLength] = sequence[index];
        }

        return -1;
    }

    public static long findEncryptionWeakness(final String sequenceStr, final int preambleLength) {
        final long[] sequence = getSequence(sequenceStr);
        final long targetSum = getFirstInvalidSum(sequence, preambleLength);

        int endIndex = 1;

        long total = sequence[0] + sequence[endIndex];

        final Deque<Long> contiguousSequence = new ArrayDeque<>();
        contiguousSequence.add(sequence[0]);
        contiguousSequence.add(sequence[endIndex]);

        while (total != targetSum) {
            if (total < targetSum) {
                final long nextNumber = sequence[++endIndex];
                total += nextNumber;
                contiguousSequence.add(nextNumber);
            } else {
                total -= contiguousSequence.pop();
            }
        }

        final List<Long> sorted = contiguousSequence.stream().sorted().collect(Collectors.toList());

        return sorted.get(0) + sorted.get(sorted.size() - 1);
    }

    private static long[] getSequence(String sequenceStr) {
        return Arrays.stream(sequenceStr.split("\n")).mapToLong(Long::parseLong).toArray();
    }
}
