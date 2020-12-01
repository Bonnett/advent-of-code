package uk.co.pete_b.advent.aoc2020;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day01 {
    public static int calculateTwoItemsSummingTo2020(final List<Integer> inputs) {
        final Map<Boolean, List<Integer>> grouped = inputs.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        final int value = findSummingTo2020WithTwoValues(grouped.get(Boolean.TRUE));
        if (value == -1) {
            return findSummingTo2020WithTwoValues(grouped.get(Boolean.FALSE));
        }

        return value;
    }

    public static int calculateThreeItemsSummingTo2020(final List<Integer> inputs) {
        final Map<Boolean, List<Integer>> grouped = inputs.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        final int value = findSummingTo2020WithThreeValues(grouped.get(Boolean.TRUE), grouped.get(Boolean.TRUE));
        if (value == -1) {
            return findSummingTo2020WithThreeValues(grouped.get(Boolean.TRUE), grouped.get(Boolean.FALSE));
        }

        return value;
    }

    private static int findSummingTo2020WithTwoValues(final List<Integer> values) {
        final Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()) {
            int valueA = iterator.next();
            iterator.remove();
            for (Integer valueB : values) {
                if (valueA + valueB == 2020) {
                    return valueA * valueB;
                }
            }
        }

        return -1;
    }

    private static int findSummingTo2020WithThreeValues(final List<Integer> listA, final List<Integer> listB) {
        for (int valueA : listA) {
            for (int valueB : listB) {
                for (int valueC : listB) {
                    if (valueA + valueB + valueC == 2020) {
                        return valueA * valueB * valueC;
                    }

                }
            }
        }

        return -1;
    }
}
