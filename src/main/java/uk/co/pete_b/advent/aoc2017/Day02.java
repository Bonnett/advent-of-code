package uk.co.pete_b.advent.aoc2017;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day02 {

    public static int calculateChecksumPart1(final String input) {
        int checksum = 0;

        final List<List<Integer>> list = getSpreadsheet(input);
        final List<Optional<Integer>> maximums = list.stream().map(x -> x.stream().max(Integer::compareTo)).collect(Collectors.toList());
        final List<Optional<Integer>> minimums = list.stream().map(x -> x.stream().min(Integer::compareTo)).collect(Collectors.toList());

        for (int i = 0; i < maximums.size(); i++) {
            Optional<Integer> max = maximums.get(i);
            Optional<Integer> min = minimums.get(i);
            if (!max.isPresent() || !min.isPresent()) {
                throw new IllegalStateException("Invalid data - no max or min value found");
            }
            checksum += max.get() - min.get();
        }

        return checksum;
    }

    public static int calculateChecksumPart2(final String input) {
        int checksum = 0;

        final List<List<Integer>> list = getSpreadsheet(input);

        for (List<Integer> items : list) {
            items.sort((o1, o2) -> (o1 > o2) ? 1 : -1);

            for (int i = 0; i<items.size(); i++) {
                for (int j = 0; j<items.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    if (items.get(i) % items.get(j) == 0) {
                        checksum += items.get(i) / items.get(j);
                    }
                }
            }
        }

        return checksum;
    }

    private static List<List<Integer>> getSpreadsheet(String input) {
        return Arrays.stream(input.split("\n")).map(x -> x.split("\\s")).map(x -> Arrays.stream(x).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
    }
}
