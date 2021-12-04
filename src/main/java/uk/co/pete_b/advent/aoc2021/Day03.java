package uk.co.pete_b.advent.aoc2021;

import java.util.*;

public class Day03 {
    public static int calculatePowerConsumption(final List<String> diagnostics) {
        final StringBuilder leastCommon = new StringBuilder();
        final StringBuilder mostCommon = new StringBuilder();

        for (int i = 0; i < diagnostics.get(0).length(); i++) {
            final Map<Boolean, Integer> map = new HashMap<>();
            for (String diagnostic : diagnostics) {
                final int value = diagnostic.charAt(i) - 48;
                map.compute((value == 0), (aBoolean, values) -> {
                    if (values == null) {
                        values = 0;
                    }
                    values++;

                    return values;
                });
            }

            if (map.get(true) > map.get(false)) {
                mostCommon.append("0");
                leastCommon.append("1");
            } else {
                mostCommon.append("1");
                leastCommon.append("0");
            }
        }

        return Integer.parseInt(mostCommon.toString(), 2) * Integer.parseInt(leastCommon.toString(), 2);
    }

    public static int calculateLifeSupportRating(final List<String> diagnostics) {
        final int o2Rating = calculateValue(diagnostics, true);
        final int co2Rating = calculateValue(diagnostics, false);

        return o2Rating * co2Rating;
    }

    private static int calculateValue(final List<String> diagnosticList, boolean findMostCommon) {
        final List<String> diagnostics = new ArrayList<>(diagnosticList);
        int digit = 0;
        while (diagnostics.size() > 1) {
            final Map<Integer, Integer> map = new HashMap<>();
            for (String diagnostic : diagnostics) {
                final int value = diagnostic.charAt(digit) - 48;
                map.compute(value, (aBoolean, values) -> {
                    if (values == null) {
                        values = 0;
                    }
                    values++;

                    return values;
                });
            }

            char valueToFind;
            if (findMostCommon) {
                if (map.get(0) > map.get(1)) {
                    valueToFind = '0';
                } else if (map.get(0) < map.get(1)) {
                    valueToFind = '1';
                } else {
                    valueToFind = '1';
                }
            } else {
                if (map.get(0) > map.get(1)) {
                    valueToFind = '1';
                } else if (map.get(0) < map.get(1)) {
                    valueToFind = '0';
                } else {
                    valueToFind = '0';
                }
            }

            final Iterator<String> iter = diagnostics.iterator();
            while (iter.hasNext()) {
                String val = iter.next();
                if (val.charAt(digit) != valueToFind) {
                    iter.remove();
                }
            }

            digit++;
        }

        return Integer.parseInt(diagnostics.get(0), 2);
    }
}
