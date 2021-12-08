package uk.co.pete_b.advent.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

public class Day08 {
    public static int countUniqueDigitOccurences(final List<String> patterns) {
        int occurences = 0;
        for (String pattern : patterns) {
            final String[] parts = pattern.split(" \\| ");
            final String[] outputs = parts[1].split(" ");
            for (String output : outputs) {
                final int length = output.length();
                if (length == 2 || length == 3 || length == 4 || length == 7) {
                    occurences++;
                }
            }
        }

        return occurences;
    }

    public static int sumOutputs(final List<String> patterns) {
        int total = 0;

        for (String pattern : patterns) {
            final String[] parts = pattern.split(" \\| ");
            final String[] inputs = parts[0].split(" ");
            final String[] outputs = parts[1].split(" ");
            final List<Set<Character>> digits = new ArrayList<>();
            final List<Set<Character>> outputDigits = new ArrayList<>();
            for (String digit : inputs) {
                final Set<Character> segments = new HashSet<>();
                for (char segment : digit.toCharArray()) {
                    segments.add(segment);
                }
                digits.add(segments);
            }

            for (String digit : outputs) {
                final Set<Character> segments = new HashSet<>();
                for (char segment : digit.toCharArray()) {
                    segments.add(segment);
                }
                digits.add(segments);
                outputDigits.add(segments);
            }

            final Map<Integer, List<Set<Character>>> digitSizes = digits.stream().collect(Collectors.groupingBy(Set::size));

            final Map<Integer, Set<Character>> digitAllocations = new HashMap<>();
            digitAllocations.put(1, digitSizes.get(2).get(0));
            digitAllocations.put(4, digitSizes.get(4).get(0));
            digitAllocations.put(7, digitSizes.get(3).get(0));
            digitAllocations.put(8, digitSizes.get(7).get(0));

            // Segments b/d are present in 4 but not 1
            final Set<Character> segmentBorD = digitAllocations.get(4).stream().filter(x -> !digitAllocations.get(1).contains(x)).collect(Collectors.toSet());

            // 5 is made up of 5 segments and contains both segments b and d
            digitAllocations.put(5, digitSizes.get(5).stream().filter(x -> x.containsAll(segmentBorD)).findFirst().orElseThrow());
            digitSizes.get(5).remove(digitAllocations.get(5));

            // 3 is made up of 5 segments and contains all the segments from digit 1
            digitAllocations.put(3, digitSizes.get(5).stream().filter(x -> x.containsAll(digitAllocations.get(1))).findFirst().orElseThrow());
            digitSizes.get(5).remove(digitAllocations.get(3));

            // 2 is thus the remaining digit made of 5 segments
            digitAllocations.put(2, digitSizes.get(5).stream().findFirst().orElseThrow());

            // 0 is made up of 6 segments and does not contain all the segments from digit 5
            digitAllocations.put(0, digitSizes.get(6).stream().filter(x -> !x.containsAll(digitAllocations.get(5))).findFirst().orElseThrow());
            digitSizes.get(6).remove(digitAllocations.get(0));

            // 9 is made up of 6 segments and contains all the segments from digit 3
            digitAllocations.put(9, digitSizes.get(6).stream().filter(x -> x.containsAll(digitAllocations.get(3))).findFirst().orElseThrow());
            digitSizes.get(6).remove(digitAllocations.get(9));

            // 6 is thus the remaining digit made up of 6 segments
            digitAllocations.put(6, digitSizes.get(6).stream().findFirst().orElseThrow());

            final Map<Set<Character>, Integer> circuitAllocations = new HashMap<>();
            for (Map.Entry<Integer, Set<Character>> entry : digitAllocations.entrySet()) {
                circuitAllocations.put(entry.getValue(), entry.getKey());
            }

            final StringBuilder sb = new StringBuilder();
            for (Set<Character> outputDigit : outputDigits) {
                sb.append(circuitAllocations.get(outputDigit));
            }

            total += Integer.parseInt(sb.toString());
        }

        return total;
    }
}