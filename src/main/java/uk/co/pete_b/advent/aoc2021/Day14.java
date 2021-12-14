package uk.co.pete_b.advent.aoc2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

    public static long calculateMostMinusLeastCommon(final List<String> rules, int iterations) {
        final Map<String, List<String>> polymerRules = new HashMap<>();

        for (String rule : rules) {
            final String[] parts = rule.split(" -> ");
            if (parts.length != 2) {
                continue;
            }

            // Each pair of letters produces two outputs
            polymerRules.put(parts[0], Arrays.asList(parts[0].charAt(0) + parts[1], parts[1] + parts[0].charAt(1)));
        }

        final Map<String, Long> instances = new HashMap<>();
        // Break out starting instance into the pairs it's made out of and count them
        final String startRule = rules.get(0);

        for (int i = 0; i < startRule.length() - 1; i++) {
            instances.compute(startRule.substring(i, i + 2), (s, count) -> {
                if (count == null) {
                    count = 0L;
                }
                count++;
                return count;
            });
        }

        for (int i = 0; i < iterations; i++) {
            final Map<String, Long> newInstances = new HashMap<>();
            // Loop through the current pairs we've got, and find what two pairs they produce - add the current count for this pair to each of those
            for (Map.Entry<String, Long> entry : instances.entrySet()) {

                for (String pair : polymerRules.get(entry.getKey())) {
                    newInstances.compute(pair, (s, count) -> {
                        if (count == null) {
                            count = 0L;
                        }
                        count += entry.getValue();

                        return count;
                    });
                }
            }

            instances.clear();
            instances.putAll(newInstances);
        }

        // Count the characters from each pair
        final Map<Character, Long> totals = new HashMap<>();
        for (Map.Entry<String, Long> entry : instances.entrySet()) {
            for (char c : entry.getKey().toCharArray()) {
                totals.compute(c, (character, count) -> {
                    if (count == null) {
                        count = 0L;
                    }
                    count += entry.getValue();

                    return count;
                });
            }
        }

        // Add the first & last letters as they only get added once - all other letters appear twice
        totals.compute(startRule.charAt(0), (s, count) -> {
            if (count == null) {
                count = 0L;
            }

            count++;

            return count;
        });

        totals.compute(startRule.charAt(startRule.length() - 1), (s, count) -> {
            if (count == null) {
                count = 0L;
            }

            count++;

            return count;
        });

        long mostCommon = Long.MIN_VALUE;
        long leastCommon = Long.MAX_VALUE;

        for (Long value : totals.values()) {
            if (value > mostCommon) {
                mostCommon = value;
            }

            if (value < leastCommon) {
                leastCommon = value;
            }
        }

        // We've double counted all numbers so halve this value
        return (mostCommon - leastCommon) / 2;
    }
}
