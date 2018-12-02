package uk.co.pete_b.advent.aoc2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 {
    public static int calculateChecksum(final List<String> input) {
        int twoCount = 0;
        int threeCount = 0;

        for (final String entry : input) {
            final Map<Character, Integer> characters = new HashMap<>();
            for (int i = 0; i < entry.length(); i++) {
                characters.compute(entry.charAt(i), (character, integer) -> {
                    if (integer == null) {
                        integer = 0;
                    }
                    return ++integer;
                });
            }

            boolean hasTwo = false;
            boolean hasThree = false;

            for (Integer character : characters.values()) {
                if (character == 2) {
                    hasTwo = true;
                }

                if (character == 3) {
                    hasThree = true;
                }
            }

            twoCount += (hasTwo) ? 1 : 0;
            threeCount += (hasThree) ? 1 : 0;
        }

        return twoCount * threeCount;
    }

    public static String similarChars(final List<String> input) {
        final StringBuilder matchingChars = new StringBuilder();
        for (final String entry : input) {
            String matching = null;
            for (final String matcher : input) {
                if (matcher.equals(entry)) {
                    continue;
                }

                int totalDiff = 0;
                for (int i = 0; i < matcher.length(); i++) {
                    if (entry.charAt(i) != matcher.charAt(i)) {
                        totalDiff++;
                    }

                    if (totalDiff > 1) {
                        break;
                    }
                }

                if (totalDiff == 1) {
                    matching = matcher;
                    break;
                }
            }


            if (matching != null) {
                for (int i = 0; i < matching.length(); i++) {
                    if (entry.charAt(i) == matching.charAt(i)) {
                        matchingChars.append(entry.charAt(i));
                    }
                }
                break;
            }

        }

        return matchingChars.toString();
    }
}
